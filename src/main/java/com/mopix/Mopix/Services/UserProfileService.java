package com.mopix.Mopix.Services;


import com.mopix.Mopix.Dtos.Request.UserProfileResponse;
import com.mopix.Mopix.Dtos.Response.UserFollowResponse;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserProfileService {
    UserProfileResponse getUserByUsername(String username) throws MopixExpection;

    Page<UserFollowResponse> getUserFollowing(String username, Pageable pageable) throws MopixExpection;

    Page<UserFollowResponse> getUserFollower(String username,Pageable pageable) throws MopixExpection;

    Page<UserFollowResponse> getUserPosts(String username, Pageable pageable) throws MopixExpection;
}
