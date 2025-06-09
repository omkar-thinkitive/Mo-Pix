package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.Request.UserCreateRequest;
import com.mopix.Mopix.Dtos.Response.UserResponse;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserCreateRequest userCreateRequest) {

        UserEntity userEntity = UserEntity.builder()
                .userName(userCreateRequest.getUserName())
                .firstname(userCreateRequest.getFirstname())
                .middlename(userCreateRequest.getMiddlename())
                .lastname(userCreateRequest.getLastname())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .build();
        userRepo.save(userEntity);
    }

    @Override
    public UserResponse getUser() {

        Optional<UserEntity> userEntity = userRepo.findById(Long.valueOf(2));
        UserEntity user = userEntity.get();

        UserResponse userResponse = UserResponse.builder()
                .userName(user.getUserName())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .password(user.getPassword())
                .build();

        return userResponse;
    }
}
