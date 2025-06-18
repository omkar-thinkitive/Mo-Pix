package com.mopix.Mopix.Dtos.Request;

import com.mopix.Mopix.Entity.UserEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PostCreateRequest {

    private String caption;
    private UUID uuid;
    private String title;
    private String fileKey;
}
