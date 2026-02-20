package com.uzum.cms.exception;

import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class CardNotFoundException extends ApplicationException{
    public CardNotFoundException(Error error) {
        super(error.getCode(), error.getMessage(), ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }
}
