package com.mopix.Mopix.Dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    private String userName;
    private String firstname;
    private String lastname;
    private String middlename;
    private String password;
}
