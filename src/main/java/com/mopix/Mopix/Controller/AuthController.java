package com.mopix.Mopix.Controller;

import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Services.AuthService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/master/v1/auth")
public class AuthController extends AppController{

    @Autowired
    private AuthService authService;

    @GetMapping("/isMobile-present")
    public ResponseEntity<Response> isMobileNumberPresent(@RequestParam String mobileNumber) throws MopixExpection {
        boolean isMobileNumber = authService.isMobileNumberPresent(mobileNumber);
        return success(ResponseCode.OK, "SingUp successfully",String.valueOf(isMobileNumber));
    }

    @GetMapping("/isEmail-present")
    public ResponseEntity<Response> isEmailPresent(@RequestParam String email) throws MopixExpection {
        boolean isMobileNumber = authService.isEmailPresent(email);
        return success(ResponseCode.OK, "SingUp successfully",String.valueOf(isMobileNumber));
    }

}
