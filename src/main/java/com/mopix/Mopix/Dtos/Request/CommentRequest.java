package com.mopix.Mopix.Dtos.Request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentRequest {

    private String userName;
    private Long postId;
    private String comment;
}
