package com.mopix.Mopix.Services;

import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface AWSService {
    String uploadFile(MultipartFile file) throws MopixExpection, IOException;

    String getPreSingedURL(String key) throws MopixExpection, IOException;
}
