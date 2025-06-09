package com.mopix.Mopix.Entity;

import com.mopix.Mopix.Dtos.enums.UserRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String firstname;
    private String lastname;
    private String middlename;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles userRole;
}
