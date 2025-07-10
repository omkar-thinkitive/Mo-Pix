package com.mopix.Mopix.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.io.InputStream;
@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @Bean
    public FirebaseApp firebaseApp() throws MopixExpection, IOException {
        InputStream serviceAccount;
        try {

            serviceAccount = getClass().getClassLoader().getResourceAsStream(firebaseConfigPath);

            if (serviceAccount == null) {
                throw new MopixExpection(ResponseCode.BAD_REQUEST, "Firebase configuration file not found in classpath: " + firebaseConfigPath);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new MopixExpection(ResponseCode.BAD_REQUEST, "Error loading Firebase configuration: " + e.getMessage());
        }
    }
}
