package com.mopix.Mopix.Controller;


import com.mopix.Mopix.Dtos.Request.LoginRequest;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Security.JwtProvider;
import com.mopix.Mopix.Services.LoginService;
import com.mopix.Mopix.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

//import static jdk.vm.ci.hotspot.HotSpotCompilationRequestResult.success;

@RestController
@RequestMapping("api/master/v1")
public class LoginController extends AppController{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );

        String token = jwtProvider.generateToken(loginRequest.getUserName());
        UUID uuid = userRepo.findByUserName(loginRequest.getUserName()).getUuid();
        return success(ResponseCode.SUCCESS, "Login successful", token,uuid);
    }

//    @GetMapping("/login/callback-google")
//    public ResponseEntity<Response> loginGoogle(@RequestParam String code){
//        String token = loginService.signUpWithGoogle(code);
//        return success(ResponseCode.OK, "SingUp successfully",token);
//    }

    @GetMapping("/login/callback-google")
    public ResponseEntity<Response> googleLogin(@RequestParam("code") String code) {
        String jwt = loginService.signUpWithGoogle(code);
        return success(ResponseCode.SUCCESS, "Login successful", jwt);
    }

    @GetMapping("/login/callback-google-ios")
    public ResponseEntity<Response> googleLoginForIos(@RequestParam("code") String code) {
        String jwt = loginService.googleLoginForIos(code);
        return success(ResponseCode.SUCCESS, "Login successful", jwt);
    }

    @GetMapping("/login-github")
    public ResponseEntity<Response> loginGithub(@RequestParam("code") String code) {
        String token = loginService.signUpWithGitHub(code);
        return success(ResponseCode.SUCCESS, "Login successful", token);
    }

    @GetMapping("/login-facebook")
    public ResponseEntity<Response> loginFacebook(@RequestParam("code") String code) {
        String token = loginService.signUpWithFacebook(code);
        return success(ResponseCode.SUCCESS, "Login successful", token);
    }

    @PostMapping("/login-apple")
    public ResponseEntity<Response> loginWithApple(@RequestParam("code") String code) {
        String token = loginService.signInWithApple(code);
        return success(ResponseCode.SUCCESS, "Login successful", token);
    }
}
