package com.mopix.Mopix.Controller;

import com.mopix.Mopix.Dtos.Request.PostCreateRequest;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.PostService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/master/v1/post")
public class PostController extends AppController{

    @Autowired
    private PostService postService;

    @PostMapping("/save-post")
    public ResponseEntity<Response> savePost(@RequestBody PostCreateRequest postCreateRequest) throws MopixExpection, IOException {
        String response = postService.savePost(postCreateRequest);
        return success(ResponseCode.SUCCESS,"Post Saved Successfully !", response);
    }
}
