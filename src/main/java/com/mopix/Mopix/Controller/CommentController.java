package com.mopix.Mopix.Controller;

import com.mopix.Mopix.Dtos.Request.CommentRequest;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.CommentService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/master/v1/comment")
public class CommentController extends AppController{

    @Autowired
    private CommentService commentService;

    @PostMapping("/save-comment")
    ResponseEntity<Response> saveComment(@RequestBody CommentRequest commentRequest) throws MopixExpection {
        commentService.saveComment(commentRequest);
        return success(ResponseCode.CREATED, "Comment on post Saved successfully");
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity<Response> deleteComment(@PathVariable Long id) throws MopixExpection{
        commentService.deleteComment(id);
        return success(ResponseCode.SUCCESS,"Comment deleted Successfully");
    }

    @PutMapping("/update/{id}")
    ResponseEntity<Response> updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) throws MopixExpection{
        commentService.updateComment(id,commentRequest);
        return success(ResponseCode.SUCCESS,"Comment updated Successfully");
    }

    @GetMapping("comment-for-post")
    ResponseEntity<Response> getAllComment(
            @PathVariable Long id,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) throws MopixExpection{
        Page<CommentRequest> list = commentService.getAllPostComment(id,pageable);
        return success(ResponseCode.SUCCESS,"Comment updated Successfully",list);
    }

}
