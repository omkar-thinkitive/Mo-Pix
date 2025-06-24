package com.mopix.Mopix.Entity;

import com.mopix.Mopix.Dtos.enums.Gender;
import com.mopix.Mopix.Dtos.enums.UserRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

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

    private UUID uuid = UUID.randomUUID();


    private String userName;
    private String firstname;
    private String lastname;
    private String middlename;
    private String password;

    private String dob;
    private String bio;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserRoles userRole;

    @Column(name = "follower_count")
    private Long followerCount;
    @Column(name = "following_count")
    private Long followingCount;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(columnDefinition = "TEXT")
    private String profileUrl;

    private boolean deleted = false;
}
