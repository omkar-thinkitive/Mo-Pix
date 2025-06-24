package com.mopix.Mopix.Services;

import com.mopix.Mopix.Dtos.Request.PostCreateRequest;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PostService {
    String savePost(PostCreateRequest postCreateRequest) throws MopixExpection, IOException;
}
