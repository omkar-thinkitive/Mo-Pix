package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.Request.UserProfileResponse;
import com.mopix.Mopix.Dtos.Response.UserFollowResponse;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.FollowerRepo;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Services.UserProfileService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FollowerRepo followerRepo;

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
}
