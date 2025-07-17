package com.mopix.Mopix.Controller;

import com.mopix.Mopix.Dtos.enums.ResponseCode;
import com.mopix.Mopix.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

public class AppController {

    public ResponseEntity<Response> success(ResponseCode code, String message, Object entity) {
        return new ResponseEntity<>(Response.builder()
                .code(code)
                .message(message)
                .data(entity)
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .requestId(UUID.randomUUID().toString())
                .version("1.0")
                .build(), code == ResponseCode.CREATED? HttpStatus.CREATED : HttpStatus.OK);
    }

    public ResponseEntity<Response> success(ResponseCode code, String message) {
        return new ResponseEntity<>(Response.builder()
                .code(code)
                .message(message)
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .requestId(UUID.randomUUID().toString())
                .version("1.0")
                .build(), code == ResponseCode.CREATED? HttpStatus.CREATED : HttpStatus.OK);
    }

    public ResponseEntity<Response> success(ResponseCode code, String message, String Token) {
        return new ResponseEntity<>(Response.builder()
                .code(code)
                .message(message)
                .data(Token)
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .requestId(UUID.randomUUID().toString())
                .version("1.0")
                .build(), code == ResponseCode.CREATED? HttpStatus.CREATED : HttpStatus.OK);
    }

    public ResponseEntity<Response> success(ResponseCode code, String message, String Token, UUID uuid) {
        return new ResponseEntity<>(Response.builder()
                .code(code)
                .message(message)
                .data(Token)
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .requestId(String.valueOf(uuid))
                .version("1.0")
                .build(), code == ResponseCode.CREATED? HttpStatus.CREATED : HttpStatus.OK);
    }
}
