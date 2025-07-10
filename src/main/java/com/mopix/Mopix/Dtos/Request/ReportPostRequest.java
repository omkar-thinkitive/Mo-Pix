package com.mopix.Mopix.Dtos.Request;

import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ReportPostRequest {

    private String userName;
    private UserEntity userEntity;
    private UUID postUUID;
    private PostEntity post;

    @NotBlank(message = "Comment must not be blank")
    private String comment;
}
