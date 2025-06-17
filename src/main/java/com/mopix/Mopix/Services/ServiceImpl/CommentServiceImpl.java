package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.Request.CommentRequest;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Entity.CommentEntity;
import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.CommentRepo;
import com.mopix.Mopix.Repository.PostRepo;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Services.CommentService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void saveComment(CommentRequest commentRequest) throws MopixExpection {

        Optional<PostEntity> postEntity = postRepo.findById(commentRequest.getPostId());
        UserEntity user = userRepo.findByUserName(commentRequest.getUserName());

        PostEntity post = postEntity.get();
        if(postEntity.isEmpty()){
            throw new MopixExpection(ResponseCode.INTERNAL_ERROR,"post not Found");
        }

        Long commentCount = post.getCommentCount();
        post.setCommentCount(++commentCount);
        postRepo.save(post);

        CommentEntity comment = new CommentEntity();
        comment.setComment(commentRequest.getComment());
        comment.setUserEntity(user);
        comment.setPostEntity(post);
        comment.setCommentAt(Instant.now());
        commentRepo.save(comment);
    }

    @Override
    public void deleteComment(Long id) throws MopixExpection {

        Optional<CommentEntity> comment = commentRepo.findById(id);
        if(comment.isEmpty()){
            throw new MopixExpection(ResponseCode.BAD_REQUEST, "Comment does not exits");
        }

        CommentEntity commentEntity = new CommentEntity();
        commentEntity = comment.get();
        commentEntity.setDeleted(true);
        commentRepo.save(commentEntity);
    }

    @Override
    public void updateComment(Long id,CommentRequest commentRequest) throws MopixExpection {

        Optional<CommentEntity> comment = commentRepo.findById(id);
        if(comment.isEmpty()){
            throw new MopixExpection(ResponseCode.BAD_REQUEST, "Comment does not exits");
        }

        CommentEntity commentEntity = new CommentEntity();
        commentEntity = comment.get();
        commentEntity.setComment(commentRequest.getComment());
        commentRepo.save(commentEntity);
    }

    @Override
    public Page<CommentRequest> getAllPostComment(Long id, Pageable pageable) throws MopixExpection {

        Page<CommentEntity> commentEntities = commentRepo.findAllByPostEntity(id,pageable);

        return commentEntities.map(comment -> {
            CommentRequest build = CommentRequest.builder()
                    .userName(comment.getUserEntity().getUserName())
                    .comment(comment.getComment())
                    .build();
            return build;
        });
    }
}
