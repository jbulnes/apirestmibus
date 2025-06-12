package com.ApiRestMiBus.controller.advice;

import com.ApiRestMiBus.common.domain.ErrorInfo;
import com.ApiRestMiBus.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorInfo> handleBadCredentials(BadCredentialsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("auth", ex.getMessage());

        ErrorInfo errorInfo = ErrorInfo.builder()
                .code("BAD_CREDENTIALS")
                .description("Credenciales inválidas")
                .errors(errors)  // O ajusta si tu clase no tiene campo `errors`
                .build();

        return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED); // 401
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleUsernameNotFound(UsernameNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("username", ex.getMessage());

        ErrorInfo errorInfo = ErrorInfo.builder()
                .code("USER_NOT_FOUND")
                .description("El usuario no fue encontrado")
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED); // o 404 si lo prefieres
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        ErrorInfo error = ErrorInfo.builder()
                .code("VALIDATION_ERROR")
                .description("Errores de validación en los campos enviados.")
                .errors(errors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY); // 422
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorInfo> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorInfo error = ErrorInfo.builder()
                .code("MALFORMED_JSON")
                .description("El formato del JSON enviado no es válido. Verifica la estructura y los campos.")
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorInfo> handleDuplicateUsernameException(DuplicateUsernameException ex) {
        ErrorInfo error = ErrorInfo.builder()
                .code(ex.getCode())
                .description(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409 Conflict
    }

    @ExceptionHandler(RolesNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleRolesNotFoundException(RolesNotFoundException ex) {
        ErrorInfo error = ErrorInfo.builder()
                .code("ROLES_NOT_FOUND")
                .description(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
