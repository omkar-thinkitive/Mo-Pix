package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.FeedDTO;
import com.mopix.Mopix.Dtos.Request.UserProfileResponse;
import com.mopix.Mopix.Dtos.Response.UserFeedResponse;
import com.mopix.Mopix.Dtos.Response.UserFollowResponse;
import com.mopix.Mopix.Dtos.dto.CommentDTO;
import com.mopix.Mopix.Dtos.dto.HashTagDTO;
import com.mopix.Mopix.Dtos.dto.PostDTO;
import com.mopix.Mopix.Dtos.dto.UserDTO;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Entity.*;
import com.mopix.Mopix.Repository.*;
import com.mopix.Mopix.Services.UserProfileService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FollowerRepo followerRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private HashTagRepo hashTagRepo;

    @Autowired
    private UserHashtagUsageRepo userHashtagUsageRepo;

    @Override
    public UserProfileResponse getUserByUsername(String username) throws MopixExpection {

        UserEntity user = userRepo.findByUserName(username);

        UserProfileResponse userProfileResponse = UserProfileResponse.builder()
                .bio(user.getBio())
                .username(user.getUserName())
                .userName(user.getFirstname()+ " " + user.getLastname())
                .profileUrl(user.getProfileUrl())
                .followerCount(user.getFollowerCount())
                .followingCount(user.getFollowingCount())
                .likesCount(user.getLikeCount())
                .build();

        return userProfileResponse;
    }

    @Override
    public Page<UserFollowResponse> getUserFollowing(String username, Pageable pageable) throws MopixExpection {

        UserEntity user = userRepo.findByUserName(username);

        Page<UserEntity> userEntities = followerRepo.findUsersEntity(user.getId(),pageable);

        return userEntities.map(userEntity ->
                {

                    UserFollowResponse build = UserFollowResponse.builder()
                            .profileUrl(userEntity.getProfileUrl())
                            .realUserName(userEntity.getFirstname()+ " "+ userEntity.getLastname())
                            .username(userEntity.getUserName())
                            .build();
                    return build;
                }
        );
    }

    @Override
    public Page<UserFollowResponse> getUserFollower(String username,Pageable pageable) throws MopixExpection {

        UserEntity user = userRepo.findByUserName(username);

        Page<UserEntity> userEntities = followerRepo.findFollowerUsersEntity(user.getId(),pageable);

        return userEntities.map(userEntity ->
                {

                    UserFollowResponse build = UserFollowResponse.builder()
                            .profileUrl(userEntity.getProfileUrl())
                            .realUserName(userEntity.getFirstname()+ " "+ userEntity.getLastname())
                            .username(userEntity.getUserName())
                            .build();
                    return build;
                }
        );
    }

    @Override
    public Page<UserFollowResponse> getUserPosts(String username, Pageable pageable) throws MopixExpection {


        return null;
    }

    @Override
    public Page<UserFeedResponse> getUserFeed(String username, Pageable pageable) throws MopixExpection {
        UserEntity userEntity = userRepo.findByUserName(username);
        if (userEntity == null) {
            throw new MopixExpection(ResponseCode.BAD_REQUEST,"User not found with username: " + username);
        }

        List<UserEntity> userEntities = followerRepo.findFollowerUsersEntity(userEntity.getId());
        if (userEntities == null || userEntities.isEmpty()) {
            return Page.empty(pageable);
        }

        Page<FeedDTO> postEntityList = postRepo.findByUser(userEntities, pageable);

        return postEntityList.map(post -> {
            PostEntity p = post.getPost();
            CommentEntity c = post.getComment();
            UserEntity u = post.getUser();

            return UserFeedResponse.builder()
                    .mediaUrl(p.getMediaUrl())
                    .post(PostDTO.builder()
                            .id(p.getId())
                            .caption(p.getCaption())
                            .mediaUrl(p.getMediaUrl())
                            .title(p.getTitle())
                            .mediaType(p.getMediaType())
                            .likeCount(p.getLikeCount())
                            .shareCount(p.getShareCount())
                            .commentCount(p.getCommentCount())
                            .isNFT(p.isNFT())
                            .build())
                    .comment(c != null ? CommentDTO.builder()
                            .id(c.getId())
                            .comment(c.getComment())
                            .commentedBy(c.getUserEntity().getUserName())
                            .commentAt(c.getCommentAt())
                            .build() : null)
                    .user(UserDTO.builder()
                            .id(u.getId())
                            .username(u.getUserName())
//                            .fullName(u.getFullName()) // adjust if needed
//                            .profilePicture(u.getProfilePicture())
                            .build())
                    .hashTags(p.getHashtags().stream()
                            .map(h -> HashTagDTO.builder()
                                    .id(h.getId())
                                    .tag(h.getName())
                                    .build())
                            .toList())
                    .build();
        });
    }

    @Override
    public Page<UserFeedResponse> getUserRandomFeed(String username, Pageable pageable) throws MopixExpection {
        return null;
    }

    public Set<HashTag> processHashtags(Set<String> tagNames, UserEntity user) {
        Set<HashTag> hashtags = new HashSet<>();

        for (String tagName : tagNames) {
            HashTag hashtag = (HashTag) hashTagRepo.findByName(tagName)
                    .orElseGet(() -> {
                        HashTag newTag = new HashTag();
                        newTag.setName(tagName);
                        newTag.setUsageCount(0L);
                        newTag.setLastUpdated(LocalDate.now());
                        return hashTagRepo.save(newTag);
                    });

            hashtag.setUsageCount(hashtag.getUsageCount() + 1);
            hashtag.setLastUpdated(LocalDate.now());
            hashTagRepo.save(hashtag);

            Optional<UserHashtagUsageEntity> usageOpt = userHashtagUsageRepo.findByUserAndHashtag(user, hashtag);
            if (usageOpt.isPresent()) {
                UserHashtagUsageEntity usage = usageOpt.get();
                usage.setUsageCount(usage.getUsageCount() + 1);
                userHashtagUsageRepo.save(usage);
            } else {
                UserHashtagUsageEntity newUsage = new UserHashtagUsageEntity();
                newUsage.setUser(user);
                newUsage.setHashtag(hashtag);
                newUsage.setUsageCount(1);
                userHashtagUsageRepo.save(newUsage);
            }

            hashtags.add(hashtag);
        }

        return hashtags;
    }


//    @Override
//    public Page<UserFeedResponse> getUserFeed(String username, Pageable pageable) throws MopixExpection {
//
//        UserEntity userEntity = userRepo.findByUserName(username);
//        List<UserEntity> userEntities = followerRepo.findFollowerUsersEntity(userEntity.getId());
//
//        Page<FeedDTO> postEntityList = postRepo.findByUser(userEntities,pageable);
//        System.out.println(postEntityList.getContent());
//        return postEntityList.map(post ->{
//            UserFeedResponse userFeedResponse = UserFeedResponse.builder()
//                    .comment(post.getComment())
//                    .post(post.getPost())
//                    .user(post.getUser())
//                    .mediaUrl(post.getPost().getMediaUrl())
//                    .build();
//            return userFeedResponse;
//        });
//    }

//    @Override
//    public Page<UserFeedResponse> getUserFeed(String username, Pageable pageable) throws MopixExpection {
//        UserEntity userEntity = userRepo.findByUserName(username);
//        List<UserEntity> userEntities = followerRepo.findFollowerUsersEntity(userEntity.getId());
//
//        Page<FeedDTO> postEntityList = postRepo.findByUser(userEntities, pageable);
//
//        return postEntityList.map(post -> UserFeedResponse.builder()
//                .postId(post.getPost().getId())
//                .caption(post.getPost().getCaption())
//                .mediaUrl(post.getPost().getMediaUrl())
//                .username(post.getUser().getUsername())
//                .commentText(post.getComment() != null ? post.getComment().getComment() : null)
//                .build());
//    }

}
