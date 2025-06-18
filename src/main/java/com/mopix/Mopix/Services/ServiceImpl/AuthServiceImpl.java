package com.mopix.Mopix.Services.ServiceImpl;

import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Services.AuthService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public boolean isMobileNumberPresent(String mobileNumber) throws MopixExpection {

        UserEntity user = userRepo.findByMobileNumber(mobileNumber);
        return user == null;
    }

    @Override
    public boolean isEmailPresent(String email) throws MopixExpection {

        UserEntity user = userRepo.findByEmail(email);
        return user == null;
    }
}
