package com.mopix.Mopix.Dtos.Request;

import com.mopix.Mopix.Dtos.enums.MediaType;
import com.mopix.Mopix.Entity.HashTag;
import com.mopix.Mopix.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class PostCreateRequest {

    private String caption;
    private UUID uuid;
    private String title;
    private String mediaUrl;
    private MediaType mediaType;
    private String description;
    private boolean isNFT;

    Set<String> primaryHashTag;
    List<String> SecondaryHashTag;
}
