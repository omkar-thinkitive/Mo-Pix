package com.mopix.Mopix.Dtos.Request;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfileResponse {

    private String profileUrl;
    private String username;
    private String userName;
    private Long followingCount;
    private Long followerCount;
    private Long likesCount;
    private String bio;

}
