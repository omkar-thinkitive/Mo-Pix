package com.mopix.Mopix.Services;

import com.mopix.Mopix.Dtos.Request.FollowRequest;
import com.mopix.Mopix.Dtos.Request.UserCreateRequest;
import com.mopix.Mopix.Dtos.Response.UserResponse;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public interface UserService {

    HashMap<String, String> saveUser(UserCreateRequest userCreateRequest) throws MopixExpection;

     UserResponse getUser(Long id);

     void savePassion(UserCreateRequest userCreateRequest);

    void saveFollower(FollowRequest followRequest);
}
