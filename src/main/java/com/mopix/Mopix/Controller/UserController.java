package com.mopix.Mopix.Controller;


import com.mopix.Mopix.Dtos.Request.CommentRequest;
import com.mopix.Mopix.Dtos.Request.FollowRequest;
import com.mopix.Mopix.Dtos.Request.UserCreateRequest;
import com.mopix.Mopix.Dtos.Response.UserResponse;
import com.mopix.Mopix.Services.CommentService;
import com.mopix.Mopix.Services.LikeService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import com.mopix.Mopix.utils.Response;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/master/v1/user")
public class UserController extends AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @PostMapping("/create")
    ResponseEntity<Response> createUser(@RequestBody UserCreateRequest userCreateRequest) throws MopixExpection {
        userService.saveUser(userCreateRequest);
        return success(ResponseCode.CREATED, "User created successfully");
    }

    @PostMapping("/save-user")
    ResponseEntity<Response> savePassion(@RequestBody UserCreateRequest userCreateRequest) throws MopixExpection {
        userService.savePassion(userCreateRequest);
        return success(ResponseCode.CREATED, "User created successfully");
    }

    @GetMapping("/id/{id}")
    ResponseEntity<Response> getUser(@RequestParam Long id) throws MopixExpection{
        UserResponse userResponse =  userService.getUser(id);
        return success(ResponseCode.CREATED, "User fetch successfully",userResponse);
    }

    @PostMapping("/like-post/{id}")
    ResponseEntity<Response> saveLikePost(@PathVariable Long id) throws MopixExpection{
        likeService.saveLikedPost(id);
        return success(ResponseCode.CREATED, "Liked Post Saved successfully");
    }

    @PostMapping("/follow")
    ResponseEntity<Response> saveFollower(@RequestBody FollowRequest followRequest) throws MopixExpection{
        userService.saveFollower(followRequest);
        return success(ResponseCode.CREATED, "Liked Post Saved successfully");
    }
}
