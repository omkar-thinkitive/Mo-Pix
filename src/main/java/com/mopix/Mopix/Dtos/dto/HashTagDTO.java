package com.mopix.Mopix.Dtos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HashTagDTO {
    private Long id;
    private String tag;
}
