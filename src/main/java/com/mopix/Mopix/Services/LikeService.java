package com.mopix.Mopix.Services;

import com.mopix.Mopix.Dtos.Response.UserResponse;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    void saveLikedPost(Long id) throws MopixExpection;
}
