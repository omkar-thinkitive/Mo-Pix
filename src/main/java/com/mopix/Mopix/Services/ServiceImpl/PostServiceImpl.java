package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.Request.PostCreateRequest;
import com.mopix.Mopix.Dtos.Request.PostShareRequest;
import com.mopix.Mopix.Dtos.Request.ReportPostRequest;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Entity.*;
import com.mopix.Mopix.Repository.*;
import com.mopix.Mopix.Services.AWSService;
import com.mopix.Mopix.Services.PostService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private PostShareRepo postShareRepo;

    @Autowired
    private ReportPostRepo reportPostRepo;

    @Override
    public String savePost(PostCreateRequest postCreateRequest) throws MopixExpection, IOException {

        UserEntity user = userRepo.findByUUID(postCreateRequest.getUuid());
        if(user == null){
            throw new MopixExpection(ResponseCode.BAD_REQUEST, "User Not Found");
        }
        PostEntity postEntity = new PostEntity();
        Set<HashTagEntity> hashTagEntities = new HashSet<>();

        if(postCreateRequest.getPrimaryHashTag() != null){
            for(String hashtag: postCreateRequest.getPrimaryHashTag()){
                HashTagEntity hashTagEntity = hashTagRepo.findHashTagByName(hashtag);
                if(hashTagEntity == null){
                    HashTagEntity hash = new HashTagEntity();
                    hash.setName(hashtag);
                    hash.setUsageCount(0L);
                    hash.setTrendScore(0);
                    hashTagRepo.save(hash);
                    hashTagEntities.add(hash);
                }else{
                    hashTagEntities.add(hashTagEntity);
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
            postEntity.setHashtags(hashTagEntities);
            postRepo.save(postEntity);
        } catch (Exception e) {
            throw new MopixExpection(ResponseCode.BAD_REQUEST,"Internal Server Error");
        }

        if(postCreateRequest.getPrimaryHashTag() != null){
            for(String hashtag : postCreateRequest.getPrimaryHashTag()){
                HashTagEntity hashTagEntity = hashTagRepo.findHashTagByName(hashtag);
                hashTagEntity.getPosts().add(postEntity);
                Long usagecount = hashTagEntity.getUsageCount();
                hashTagEntity.setUsageCount(usagecount +1);
                double trendScore = hashTagEntity.getTrendScore();
                hashTagEntity.setTrendScore(trendScore+1);
                hashTagEntity.setLastUpdated(LocalDate.now());
                hashTagRepo.save(hashTagEntity);
            }
        }
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

    @Override
    public void sharePost(PostShareRequest postShareRequest) throws MopixExpection {

        UserEntity user = userRepo.findByUserName(postShareRequest.getSenderUser());
        PostEntity post = postRepo.getPostByUUID(postShareRequest.getPostUUID());
        List<UserEntity> userEntities = new ArrayList<>();

        PostShareEntity postShareEntity = new PostShareEntity();
        postShareEntity.setUserEntity(user);
        postShareEntity.setPost(post);
        postShareEntity.setSharedAt(LocalDateTime.now());
        postShareRepo.save(postShareEntity);

        List<PostShareRecipientEntity> recipients = postShareRequest.getListReciverUser().stream().map(recipientId -> {
            UserEntity recipient = userRepo.findByUserName(recipientId);
//                    .orElseThrow(() -> new MopixExpection(ResponseCode.BAD_REQUEST,"Recipient not found: "));
            PostShareRecipientEntity recipientEntity = new PostShareRecipientEntity();
            recipientEntity.setPostShare(postShareEntity);
            recipientEntity.setRecipient(recipient);
            recipientEntity.setSeen(false);
            return recipientEntity;
        }).collect(Collectors.toList());

        postShareEntity.setRecipients(recipients);
        postShareRepo.save(postShareEntity);
    }


}
