package com.mopix.Mopix.Dtos.dto;

import com.mopix.Mopix.Dtos.enums.MediaType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {
    private Long id;
    private String caption;
    private String mediaUrl;
    private String title;
    private MediaType mediaType;
    private Long likeCount;
    private Long shareCount;
    private Long commentCount;
    private boolean isNFT;
}
