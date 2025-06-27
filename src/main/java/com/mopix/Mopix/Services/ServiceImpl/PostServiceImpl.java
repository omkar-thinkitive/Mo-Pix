package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.Request.PostCreateRequest;
import com.mopix.Mopix.Dtos.enums.MediaType;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Entity.HashTag;
import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.HashTagRepo;
import com.mopix.Mopix.Repository.PostRepo;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Services.AWSService;
import com.mopix.Mopix.Services.PostService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private AWSService awsService;

    @Autowired
    private HashTagRepo hashTagRepo;

    @Override
    public String savePost(PostCreateRequest postCreateRequest) throws MopixExpection, IOException {

        UserEntity user = userRepo.findByUUID(postCreateRequest.getUuid());
        if(user == null){
            throw new MopixExpection(ResponseCode.BAD_REQUEST, "User Not Found");
        }
        PostEntity postEntity = new PostEntity();
        Set<HashTag> hashTags = new HashSet<>();

        if(postCreateRequest.getPrimaryHashTag() != null){
            for(String hashtag: postCreateRequest.getPrimaryHashTag()){
                HashTag hashTag = hashTagRepo.findHashTagByName(hashtag);
                if(hashTag == null){
                    HashTag hash = new HashTag();
                    hash.setName(hashtag);
                    hash.setUsageCount(0L);
                    hash.setTrendScore(0);
                    hashTagRepo.save(hash);
                    hashTags.add(hash);
                }else{
                    hashTags.add(hashTag);
                }
            }
        }
        try {
            String mediaPreSignedUrl = awsService.getPreSingedURL(postCreateRequest.getMediaUrl());

            postEntity.setPostUUID(UUID.randomUUID());
            postEntity.setUserEntity(user);
            postEntity.setTitle(postEntity.getTitle());
            postEntity.setDescription(postEntity.getDescription());
            postEntity.setMediaUrl(mediaPreSignedUrl);
            postEntity.setNFT(postEntity.isNFT());
            postEntity.setMediaType(postCreateRequest.getMediaType());
            postEntity.setHashtags(hashTags);
            postRepo.save(postEntity);
        } catch (Exception e) {
            throw new MopixExpection(ResponseCode.BAD_REQUEST,"Internal Server Error");
        }

//        if(postCreateRequest.getPrimaryHashTag() != null){
//            for(String hashtag : postCreateRequest.getPrimaryHashTag()){
//                HashTag hashTag = hashTagRepo.findHashTagByName(hashtag);
//                hashTag.getPosts().add(postEntity);
//                hashTag.setUsageCount(hashTag.getUsageCount() +1);
//                hashTag.setTrendScore(hashTag.getTrendScore()+1);
//                hashTag.setLastUpdated(LocalDate.now());
//                hashTagRepo.save(hashTag);
//            }
//        }
        return String.valueOf(postEntity.getPostUUID());
    }

    @Override
    public PostEntity getPost(UUID uuid) throws MopixExpection {
        PostEntity postEntity = postRepo.getPostByUUID(uuid);
        if(postEntity == null){
            throw new MopixExpection(ResponseCode.BAD_REQUEST,"Post Not exits !");
        }
        return postEntity;
    }
}
