package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Entity.LikeEntity;
import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.LikeRepo;
import com.mopix.Mopix.Repository.PostRepo;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Security.JwtAuthenticationFilter;
import com.mopix.Mopix.Services.LikeService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private PostRepo postRepo;

    @Override
    public void saveLikedPost(Long id)throws MopixExpection {

        String username = jwtAuthenticationFilter.userNameFromToken;
        UserEntity userEntity = userRepo.findByUserName(username);
        if(userEntity == null){
            return;
        }

        Optional<PostEntity> post = postRepo.findById(id);
        if(post.isEmpty()){
            return;
        }
        PostEntity postEntity = post.get();
        Long likeCount = postEntity.getLikeCount();
        postEntity.setLikeCount(++likeCount);
        postRepo.save(postEntity);

        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setPostEntity(postEntity);
        likeEntity.setUserEntity(userEntity);
        likeEntity.setLikedAt(Instant.now());
        likeRepo.save(likeEntity);
    }

    @Override
    public void saveFollower(String username) {



    }
}
