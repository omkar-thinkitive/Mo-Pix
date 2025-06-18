package com.mopix.Mopix.utils.Expection;

import com.mopix.Mopix.Dtos.enums.ResponseCode;
import lombok.Getter;

public class MopixExpection extends Exception{

    @Getter
    private ResponseCode errorCode;
    private String[] fields;
    private Exception exception;

    public MopixExpection() {
        super("Failed to do operation");
        this.errorCode = ResponseCode.INTERNAL_ERROR;
        this.exception = new RuntimeException();
    }

    public MopixExpection(ResponseCode code, String message, String... fields) {
        super(message);
        this.errorCode = code;
        this.fields = fields;
        this.exception= new RuntimeException(message);
    }

    public MopixExpection(Exception exception) {
        super(exception.getLocalizedMessage());
        this.errorCode = ResponseCode.INTERNAL_ERROR;
        this.exception = exception;
    }

}
