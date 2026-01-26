package com.uzum.cms.exception;

import com.uzum.cms.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class CardNotFoundException extends ApplicationException{
    public CardNotFoundException(String message) {
        super(10011, message, ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }
}
