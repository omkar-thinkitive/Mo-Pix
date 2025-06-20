package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.Request.UserCreateRequest;
import com.mopix.Mopix.Dtos.Response.UserResponse;
import com.mopix.Mopix.Dtos.enums.Passion;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Entity.PassionEntity;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.PassionRepo;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Services.UserService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PassionRepo passionRepo;

    @Override
    public void saveUser(UserCreateRequest userCreateRequest)throws MopixExpection {

        UserEntity user = userRepo.findByUserName(userCreateRequest.getUserName());
        if(user != null){
            throw new MopixExpection(ResponseCode.BAD_REQUEST, "UserName Already Exits !");
        }

        UserEntity userEntity = UserEntity.builder()
                .userName(userCreateRequest.getUserName())
                .uuid(UUID.randomUUID())
                .firstname(userCreateRequest.getFirstname())
                .middlename(userCreateRequest.getMiddlename())
                .lastname(userCreateRequest.getLastname())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .phone(userCreateRequest.getPhone())
                .build();
        userRepo.save(userEntity);

        if(userCreateRequest.getPassionList() != null){
            PassionEntity passion = new PassionEntity();
            List<PassionEntity> passionList = new ArrayList<>();

            List<Passion> list = userCreateRequest.getPassionList();
            for(Passion it: list){
                passionList.add(new PassionEntity(userEntity,it));
            }
            passionRepo.saveAll(passionList);
        }

    }

    @Override
    public UserResponse getUser(Long id) {

        Optional<UserEntity> userEntity = userRepo.findById(Long.valueOf(id));
        UserEntity user = userEntity.get();

        UserResponse userResponse = UserResponse.builder()
                .userName(user.getUserName())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .password(user.getPassword())
                .build();

        return userResponse;
    }

    @Override
    public void savePassion(UserCreateRequest userCreateRequest) {

        UserEntity userEntity = userRepo.findByUserName(userCreateRequest.getUserName());

        if(userCreateRequest.getPassionList() != null){
            PassionEntity passion = new PassionEntity();
            List<PassionEntity> passionList = new ArrayList<>();

            List<Passion> list = userCreateRequest.getPassionList();
            for(Passion it: list){
                passionList.add(new PassionEntity(userEntity,it));
            }
            passionRepo.saveAll(passionList);
        }

    }
}
