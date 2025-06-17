package com.mopix.Mopix.Controller;

import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.AWSService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/master/v1/aws")
public class AWSController extends AppController{

    @Autowired
    private AWSService awsService;


    @PostMapping("/upload-file")
    ResponseEntity<Response> uploadFile(@RequestParam("file")MultipartFile file) throws MopixExpection, IOException {
        String response = awsService.uploadFile(file);
        return success(ResponseCode.SUCCESS,"File upload Successfully !",response);
    }

    @GetMapping("/pre-singed-url")
    ResponseEntity<Response> getPreSingedURL(@RequestParam(value = "key") String key) throws MopixExpection, IOException{
        String preSingedUrl = awsService.getPreSingedURL(key);
        return success(ResponseCode.SUCCESS,"File upload Successfully !",preSingedUrl);

    }

}
