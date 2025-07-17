package com.mopix.Mopix.Dtos.Request;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class UserProfileResponse {

    private String profileUrl;
    private UUID uuid;
    private String username;
    private String userName;
    private Long followingCount;
    private Long followerCount;
    private Long likesCount;
    private String bio;

}
