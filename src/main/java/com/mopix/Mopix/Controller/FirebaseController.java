package com.mopix.Mopix.Controller;

import com.mopix.Mopix.Dtos.Request.FirebaseRequest;
import com.mopix.Mopix.Dtos.Request.PostShareRequest;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.FirebaseService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/master/v1/firebase")
public class FirebaseController extends AppController{

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping("/device-token")
    public ResponseEntity<Response> setDeviceToken(@RequestBody FirebaseRequest firebaseRequest) throws MopixExpection, IOException {
        firebaseService.saveDeviceToken(firebaseRequest);
        return success(ResponseCode.SUCCESS,"Device Token saved Successfully !");
    }
}
