package com.mopix.Mopix.Services;

import com.mopix.Mopix.Dtos.Request.PostCreateRequest;
import com.mopix.Mopix.Dtos.Request.PostShareRequest;
import com.mopix.Mopix.Dtos.Request.ReportPostRequest;
import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public interface PostService {
    String savePost(PostCreateRequest postCreateRequest) throws MopixExpection, IOException;

    PostEntity getPost(UUID uuid) throws MopixExpection;

    void sharePost(PostShareRequest postShareRequest) throws MopixExpection;

//    String reportPost(ReportPostRequest reportPostRequest) throws MopixExpection;
}
