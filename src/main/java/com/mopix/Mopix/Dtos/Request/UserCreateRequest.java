package com.mopix.Mopix.Dtos.Request;

import com.mopix.Mopix.Dtos.enums.Gender;
import com.mopix.Mopix.Dtos.enums.Passion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,}$",
            message = "Password must be at least 5 characters long and include 1 uppercase letter, 1 number, and 1 special character"
    )
    private String password;
    private String dob;
    private String bio;

    @Pattern(
            regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Invalid Email Address"
    )
    private String email;
    private String phone;
    private Gender gender;

    List<Passion> passionList;
}
