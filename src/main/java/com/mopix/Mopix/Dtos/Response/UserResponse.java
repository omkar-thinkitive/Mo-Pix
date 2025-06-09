package com.mopix.Mopix.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {

    private String userName;
    private String firstname;
    private String lastname;
    private String middlename;
    private String password;
}
