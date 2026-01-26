package com.uzum.cms.exception;

import com.uzum.cms.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class CardNonValidException extends ApplicationException{
    public CardNonValidException(String message) {
        super(10011, message, ErrorType.INTERNAL, HttpStatus.BAD_REQUEST);
    }

}
