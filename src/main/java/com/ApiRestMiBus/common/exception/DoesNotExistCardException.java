package com.ApiRestMiBus.common.exception;

import lombok.Generated;
import org.springframework.http.HttpStatus;

public class DoesNotExistCardException extends RuntimeException{
    private String code;
    private HttpStatus status;

    public DoesNotExistCardException(String code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }

    @Generated
    public void setCode(final String code) {
        this.code = code;
    }

    @Generated
    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    @Generated
    public String getCode() {
        return this.code;
    }

    @Generated
    public HttpStatus getStatus() {
        return this.status;
    }
}
