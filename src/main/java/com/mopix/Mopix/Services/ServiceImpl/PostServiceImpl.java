package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.Request.PostCreateRequest;
import com.mopix.Mopix.Dtos.enums.MediaType;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.PostRepo;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Services.AWSService;
import com.mopix.Mopix.Services.PostService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private AWSService awsService;

    @Override
    public String savePost(PostCreateRequest postCreateRequest) throws MopixExpection, IOException {

        UserEntity user = userRepo.findByUUID(postCreateRequest.getUuid());
        if(user == null){
            throw new MopixExpection(ResponseCode.BAD_REQUEST, "User Not Found");
        }
        PostEntity postEntity = new PostEntity();


        try {
            String mediaPreSignedUrl = awsService.getPreSingedURL(postCreateRequest.getMediaUrl());

            postEntity.setPostUUID(UUID.randomUUID());
            postEntity.setUserEntity(user);
            postEntity.setTitle(postEntity.getTitle());
            postEntity.setDescription(postEntity.getDescription());
            postEntity.setMediaUrl(mediaPreSignedUrl);
            postEntity.setNFT(postEntity.isNFT());
            postEntity.setMediaType(MediaType.IMAGE);
            postRepo.save(postEntity);
        } catch (Exception e) {
            throw new MopixExpection(ResponseCode.BAD_REQUEST,"Internal Server Error");
        }
        return String.valueOf(postEntity.getPostUUID());
    }
}
