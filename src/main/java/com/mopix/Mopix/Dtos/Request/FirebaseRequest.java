package com.mopix.Mopix.Dtos.Request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class FirebaseRequest {

    private String username;
    private UUID userUUID;
    private String deviceToken;
}
