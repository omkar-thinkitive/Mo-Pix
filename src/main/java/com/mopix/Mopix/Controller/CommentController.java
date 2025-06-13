package com.mopix.Mopix.Controller;

import com.mopix.Mopix.Dtos.Request.CommentRequest;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.CommentService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
