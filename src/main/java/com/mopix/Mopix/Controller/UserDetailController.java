package com.mopix.Mopix.Controller;

import com.mopix.Mopix.Dtos.Response.UserResponse;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.UserService;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/master/v1/users")
public class UserDetailController extends AppController{

    @Autowired
    private UserService userService;


//    @GetMapping("/id")
//    ResponseEntity<Response> getUser(){
//        UserResponse userResponse =  userService.getUser();
//        return success(ResponseCode.SUCCESS, "User created sucessfully",userResponse);
//    }
}
