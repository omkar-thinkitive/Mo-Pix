package com.mopix.Mopix.Services;

import com.mopix.Mopix.Dtos.Request.FirebaseRequest;
import com.mopix.Mopix.utils.Expection.MopixExpection;

import javax.management.monitor.MonitorSettingException;

public interface FirebaseService {
    void saveDeviceToken(FirebaseRequest firebaseRequest) throws MopixExpection;
}
