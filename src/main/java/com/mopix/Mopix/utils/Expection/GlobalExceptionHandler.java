//package com.mopix.Mopix.utils.Expection;
//
//import com.mopix.Mopix.Dtos.enums.ResponseCode;
//import com.mopix.Mopix.utils.Expection.MopixExpection;
//import com.mopix.Mopix.utils.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import java.util.UUID;
//
//import static com.mopix.Mopix.Dtos.enums.ResponseCode.BAD_REQUEST;
//import static com.mopix.Mopix.Dtos.enums.ResponseCode.INTERNAL_ERROR;
//
//@RestControllerAdvice
////@org.springdoc.core.annotations.RouterOperation(hidden = true)/
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(MopixExpection.class)
//    public ResponseEntity<Response> handleMopixExpection(MopixExpection ex) {
//        HttpStatus status = switch (ex.getErrorCode()) {
//            case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
//            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
//            default -> HttpStatus.BAD_REQUEST;
//        };
//
//        Response response = Response.builder()
//                .code(ex.getErrorCode())
//                .message(ex.getMessage())
//                .data(null)
//                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
//                .requestId(UUID.randomUUID().toString())
//                .version("1.0")
//                .build();
//
//        return new ResponseEntity<>(response, status);
//    }
//}
