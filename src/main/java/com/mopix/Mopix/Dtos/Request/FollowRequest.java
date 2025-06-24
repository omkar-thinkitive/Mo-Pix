package com.mopix.Mopix.Dtos.Request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FollowRequest {

    private String currentUser;
    private String followerUser;
}
