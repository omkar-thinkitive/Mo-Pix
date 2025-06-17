package com.mopix.Mopix.Services;

import com.mopix.Mopix.Dtos.Request.CommentRequest;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    void saveComment(CommentRequest commentRequest) throws MopixExpection;

    void deleteComment(Long id) throws MopixExpection;

    void updateComment(Long id,CommentRequest commentRequest) throws MopixExpection;

    Page<CommentRequest> getAllPostComment(Long id, Pageable pageable)throws MopixExpection;
}
