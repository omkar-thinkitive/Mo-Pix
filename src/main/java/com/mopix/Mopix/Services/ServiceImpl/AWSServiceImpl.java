package com.mopix.Mopix.Services.ServiceImpl;


import com.mopix.Mopix.Services.AWSService;
import com.mopix.Mopix.utils.Expection.MopixExpection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content; // ✅ Correct
import software.amazon.awssdk.services.ses.model.Destination; // ✅ Correct
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public  class AWSServiceImpl implements AWSService {

    @Value("${aws.s3.bucket-name}")
    private String s3Bucket;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3Presigner;

    @Autowired
    private SesClient sesClient;


    @Override
    public String uploadFile(MultipartFile file) throws MopixExpection, IOException {

        byte[] decodedBytes = file.getBytes();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3Bucket)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest,RequestBody.fromBytes(decodedBytes));

        if(putObjectResponse != null && putObjectResponse.sdkHttpResponse().isSuccessful()){
            return fileName;
        }else {
            throw new IOException("Failed to upload file to the server (S3)");
        }
    }

    @Override
    public String getPreSingedURL(String key) throws MopixExpection, IOException {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(key)
                    .build();

            GetObjectPresignRequest request = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(15))
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(request);
            return presignedGetObjectRequest.url().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(String toEmail, String subject, String body) {
        // ✅ Build Destination with toAddresses as List<String>
//        toEmail = "deshmukhomkar772@gmail.com";
//        subject = "Do Your Work !";
//        body = "Hi";
        Destination destination = Destination.builder()
                .toAddresses(Collections.singletonList(toEmail))
                .build();

        Content subjectContent = Content.builder()
                .data(subject)
                .build();

        Content bodyContent = Content.builder()
                .data(body)
                .build();

        Body emailBody = Body.builder()
                .text(bodyContent)
                .build();

        Message message = Message.builder()
                .subject(subjectContent)
                .body(emailBody)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .source("omkar.deshmukh@thinkitive.com") // must be verified in AWS SES
                .destination(destination)
                .message(message)
                .build();

        sesClient.sendEmail(emailRequest);
    }
}
