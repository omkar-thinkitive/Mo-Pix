package com.mopix.Mopix.Dtos.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CommentDTO {
    private Long id;
    private String comment;
    private String commentedBy;
    private Instant commentAt;
}
