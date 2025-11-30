package com.maxim.lab1.model.exception;

import jakarta.validation.ValidationException;
import lombok.Getter;

/**
 * Исключения связанные с "бизнес"-логикой
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCodes errorCode;

    /**
     * @param errorCode код ошибки для машинной обработки
     * @param message Человеко читаемое сообщение
     */
    public BusinessException(ErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
