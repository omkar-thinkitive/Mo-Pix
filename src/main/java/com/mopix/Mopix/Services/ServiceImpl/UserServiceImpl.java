package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Dtos.Request.FollowRequest;
import com.mopix.Mopix.Dtos.Request.UserCreateRequest;
import com.mopix.Mopix.Dtos.Response.UserResponse;
import com.mopix.Mopix.Dtos.enums.Passion;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Entity.FollowerEntity;
import com.mopix.Mopix.Entity.PassionEntity;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.FollowerRepo;
import com.mopix.Mopix.Repository.PassionRepo;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Security.JwtProvider;
import com.mopix.Mopix.Services.UserService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    @Autowired
    private FollowerRepo followerRepo;

    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public String saveUser(UserCreateRequest userCreateRequest)throws MopixExpection {

        UserEntity user = userRepo.findByUserName(userCreateRequest.getUserName());
        if(user != null){
            throw new MopixExpection(ResponseCode.BAD_REQUEST, "UserName Already Exits !");
        }

        if(userCreateRequest.getEmail() != null){
            UserEntity email = userRepo.findByEmail(userCreateRequest.getEmail());
            if(email != null){
                throw new MopixExpection(ResponseCode.BAD_REQUEST, "Email Already Exits !");
            }
        }

        if(userCreateRequest.getPhone() != null){
            UserEntity phone = userRepo.findByPhone(userCreateRequest.getPhone());
            if(phone != null){
                throw new MopixExpection(ResponseCode.BAD_REQUEST, "Phone Number Already Exits !");
            }
        }


        UserEntity userEntity = UserEntity.builder()
                .userName(userCreateRequest.getUserName())
                .uuid(UUID.randomUUID())
                .firstname(userCreateRequest.getFirstname())
                .middlename(userCreateRequest.getMiddlename())
                .lastname(userCreateRequest.getLastname())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .phone(userCreateRequest.getPhone())
                .email(userCreateRequest.getEmail())
                .build();
        userRepo.save(userEntity);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userCreateRequest.getUserName(), userCreateRequest.getPassword())
        );

        String token = jwtProvider.generateToken(userCreateRequest.getUserName());
        return  token;

//        if(userCreateRequest.getPassionList() != null){
//            PassionEntity passion = new PassionEntity();
//            List<PassionEntity> passionList = new ArrayList<>();
//
//            List<Passion> list = userCreateRequest.getPassionList();
//            for(Passion it: list){
//                passionList.add(new PassionEntity(userEntity,it));
//            }
//            passionRepo.saveAll(passionList);
//        }

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

    @Override
    public void saveFollower(FollowRequest followRequest) {

        UserEntity user1 = userRepo.findByUserName(followRequest.getCurrentUser());
        UserEntity user2 = userRepo.findByUserName(followRequest.getFollowerUser());

        FollowerEntity followerEntity = new FollowerEntity();
        followerEntity.setFollower(user1);
        followerEntity.setFollowing(user2);
        followerEntity.setUpdatedAt(Instant.now());
        followerRepo.save(followerEntity);

    }

    public UserResponse getUser1(String username) {

        UserEntity user = userRepo.findByUserName(username);

        UserResponse userResponse = UserResponse.builder()
                .userName(user.getUserName())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .password(user.getPassword())
                .build();

        return userResponse;
    }




}
