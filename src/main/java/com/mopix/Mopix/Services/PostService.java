package com.mopix.Mopix.Services;

import com.mopix.Mopix.Dtos.Request.PostCreateRequest;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    void savePost(PostCreateRequest postCreateRequest) throws MopixExpection;
}
