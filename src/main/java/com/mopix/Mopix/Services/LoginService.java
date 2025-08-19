package com.mopix.Mopix.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Map;

@Service
public interface LoginService {
    String signUpWithGoogle(String code);

    String signUpWithGitHub(String code);

    String signUpWithFacebook(String code);

    String signInWithApple(@RequestBody Map<String, String> request) throws IOException, ParseException;

    String googleLoginForIos(String code);

    String googleLoginForAndroid(@RequestBody Map<String, String> request) throws GeneralSecurityException, IOException;

    String signInApple(String code) throws Exception;

    String getPrivacyPolicy();

    String getDataDeletionInstructions();
}
