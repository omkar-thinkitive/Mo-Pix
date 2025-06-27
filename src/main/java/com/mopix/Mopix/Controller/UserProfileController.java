package com.mopix.Mopix.Controller;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.mopix.Mopix.Dtos.Request.UserProfileResponse;
import com.mopix.Mopix.Dtos.Response.UserFeedResponse;
import com.mopix.Mopix.Dtos.Response.UserFollowResponse;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.UserProfileService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/master/v1/user")
public class UserProfileController extends AppController{

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{username}")
    ResponseEntity<Response> getUserByUserName(@RequestParam("username") String username) throws MopixExpection{
        UserProfileResponse userProfileResponse = userProfileService.getUserByUsername(username);
        return success(ResponseCode.SUCCESS, "User fetched successfully",userProfileResponse);
    }

    @GetMapping("/{username}/following")
    ResponseEntity<Response> getUserFollowing(@RequestParam("username") String username, @org.springdoc.core.annotations.ParameterObject Pageable pageable) throws MopixExpection{
        Page<UserFollowResponse> userProfileResponse = userProfileService.getUserFollowing(username,pageable);
        return success(ResponseCode.SUCCESS, "User fetched successfully",userProfileResponse);
    }

    @GetMapping("/{username}/follower")
    ResponseEntity<Response> getUserFollower(@RequestParam("username") String username,@org.springdoc.core.annotations.ParameterObject Pageable pageable) throws MopixExpection{
        Page<UserFollowResponse> userProfileResponse = userProfileService.getUserFollower(username,pageable);
        return success(ResponseCode.SUCCESS, "User fetched successfully",userProfileResponse);
    }

    @GetMapping("/{username}/post")
    ResponseEntity<Response> getUserPosts(@RequestParam("username") String username,@org.springdoc.core.annotations.ParameterObject Pageable pageable) throws MopixExpection{
        Page<UserFollowResponse> userProfileResponse = userProfileService.getUserPosts(username,pageable);
        return success(ResponseCode.SUCCESS, "User fetched successfully",userProfileResponse);
    }

    @GetMapping("/{username}/feed/following")
    ResponseEntity<Response> getUserFeed(@RequestParam("username") String username,@org.springdoc.core.annotations.ParameterObject Pageable pageable) throws MopixExpection{
        Page<UserFeedResponse> userFeedResponse = userProfileService.getUserFeed(username,pageable);
        return success(ResponseCode.SUCCESS, "User fetched successfully",userFeedResponse);
    }

    @GetMapping("/{username}/feed")
    ResponseEntity<Response> getUserRandomFeed(@RequestParam("username") String username,@org.springdoc.core.annotations.ParameterObject Pageable pageable) throws MopixExpection{
        Page<UserFeedResponse> userFeedResponse = userProfileService.getUserRandomFeed(username,pageable);
        return success(ResponseCode.SUCCESS, "User fetched successfully",userFeedResponse);
    }

}
