package com.mopix.Mopix.Services;

import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUserName(username);
//                .orElseThrow(()-> new UsernameNotFoundException("User does Found: "+ username));

        if(userEntity != null){
            UserDetails userDetails = User.builder()
                    .username(userEntity.getUserName())
                    .password(userEntity.getPassword())
                    .roles(String.valueOf(userEntity.getUserRole()))
                    .build();
            return  userDetails;
        }
        throw new UsernameNotFoundException("User does Found: "+ username);
    }
}
