package com.mopix.Mopix.Dtos.Request;

import com.mopix.Mopix.Dtos.enums.Gender;
import com.mopix.Mopix.Dtos.enums.Passion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    private String userName;
    private String firstname;
    private String lastname;
    private String middlename;
    private String password;
    private String dob;
    private String bio;
    private String email;
    private String phone;
    private Gender gender;

    List<Passion> passionList;
}
