package com.mopix.Mopix.Services;

import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String signUpWithGoogle(String code);

    String signUpWithGitHub(String code);

    String signUpWithFacebook(String code);

    String signInWithApple(String code);

    String googleLoginForIos(String code);
}
