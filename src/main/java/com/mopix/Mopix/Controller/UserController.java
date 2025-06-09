package com.mopix.Mopix.Controller;


import com.mopix.Mopix.Dtos.Request.UserCreateRequest;
import com.mopix.Mopix.Dtos.Response.UserResponse;
import com.mopix.Mopix.utils.Response;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/master/v1/user")
public class UserController extends AppController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    ResponseEntity<Response> createUser(@RequestBody UserCreateRequest userCreateRequest){
        userService.saveUser(userCreateRequest);
        return success(ResponseCode.CREATED, "User created sucessfully");
    }

    @GetMapping("/id")
    ResponseEntity<Response> getUser(){
        UserResponse userResponse =  userService.getUser();
        return success(ResponseCode.CREATED, "User created sucessfully",userResponse);
    }

}
