package com.ApiRestMiBus.controller.advice;

import com.ApiRestMiBus.common.domain.ErrorInfo;
import com.ApiRestMiBus.common.exception.DoesNotExistCardException;
import com.ApiRestMiBus.common.exception.DoesNotExistTypeOperationException;
import com.ApiRestMiBus.common.exception.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {
    public GlobalAdvice() {
    }

    @ExceptionHandler({DoesNotExistCardException.class})
    public ResponseEntity<ErrorInfo> doesnotexistcardException(DoesNotExistCardException ex) {
        ErrorInfo error = ErrorInfo.builder().code(ex.getCode()).description(ex.getMessage()).build();
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InsufficientFundsException.class})
    public ResponseEntity<ErrorInfo> insufficientFundsException(InsufficientFundsException ex) {
        ErrorInfo error = ErrorInfo.builder().code(ex.getCode()).description(ex.getMessage()).build();
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DoesNotExistTypeOperationException.class})
    public ResponseEntity<ErrorInfo> doesNotExistTypeOperationException(DoesNotExistTypeOperationException ex) {
        ErrorInfo error = ErrorInfo.builder().code(ex.getCode()).description(ex.getMessage()).build();
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
