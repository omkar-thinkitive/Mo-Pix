package com.mopix.Mopix.Services.ServiceImpl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.mopix.Mopix.Dtos.Request.FirebaseRequest;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Services.FirebaseService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void saveDeviceToken(FirebaseRequest firebaseRequest) throws MopixExpection {

        UserEntity userEntity = userRepo.findByUserName(firebaseRequest.getUsername());
        if(userEntity == null){
            throw new MopixExpection(ResponseCode.BAD_REQUEST,"User Not Found");
        }
        userEntity.setDeviceToken(firebaseRequest.getDeviceToken());
        userRepo.save(userEntity);
    }

    public boolean sendPushNotification(String targetToken, String title, String body,String screen) throws MopixExpection {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
        Map<String, String> data = new HashMap<>();
        data.put("screen", screen);
        Message message = Message.builder()
                .setNotification(notification)
                .setToken(targetToken)
                .putAllData(data)
                .build();
        try {

            String response = FirebaseMessaging.getInstance().send(message);
//            logger.info("response-->"+response);
            return response != null && !response.isEmpty();
        } catch (Exception e) {
            throw new MopixExpection(ResponseCode.BAD_REQUEST, "Error sending push notification: " + e.getMessage());
        }
    }
}
