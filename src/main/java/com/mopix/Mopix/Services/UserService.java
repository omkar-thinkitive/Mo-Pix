package com.mopix.Mopix.Services;

import com.mopix.Mopix.Dtos.Request.UserCreateRequest;
import com.mopix.Mopix.Dtos.Response.UserResponse;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

     void saveUser(UserCreateRequest userCreateRequest) throws MopixExpection;

     UserResponse getUser();
}
