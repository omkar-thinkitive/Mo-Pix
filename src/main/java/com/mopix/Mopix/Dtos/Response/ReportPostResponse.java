package com.mopix.Mopix.Dtos.Response;

import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ReportPostResponse {

    private PostEntity postEntity;
    private UUID reportUUID;
    private UserEntity user;
    private String comment;
    private boolean isInformUSer;
    private boolean isTakenAction;
    private boolean isResolve;
}
