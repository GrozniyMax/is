package com.maxim.lab1.controller;

import com.maxim.is.generated.dto.ErrorResponse;
import com.maxim.lab1.model.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error(e.getMessage());

        return ResponseEntity.status(422).body(new ErrorResponse()
                .code(e.getErrorCode().toString())
                .message(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.internalServerError().body(new ErrorResponse()
                .code(e.getClass().getSimpleName())
                .message(e.getMessage())
        );

    }
}
