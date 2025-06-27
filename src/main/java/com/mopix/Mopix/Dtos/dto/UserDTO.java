package com.mopix.Mopix.Dtos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private String profilePicture;
}
