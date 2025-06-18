package com.mopix.Mopix.Services;

import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    boolean isMobileNumberPresent(String mobileNumber) throws MopixExpection;

    boolean isEmailPresent(String email)throws MopixExpection;
}
