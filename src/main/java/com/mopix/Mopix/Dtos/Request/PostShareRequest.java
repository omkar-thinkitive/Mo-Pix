package com.mopix.Mopix.Dtos.Request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class PostShareRequest {

    private String senderUser;
    private UUID postUUID;
    List<String> listReciverUser;
}
