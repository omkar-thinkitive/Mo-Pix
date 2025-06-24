package com.mopix.Mopix.Dtos.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserFollowResponse {

    private String profileUrl;
    private String realUserName;
    private String username;
}
