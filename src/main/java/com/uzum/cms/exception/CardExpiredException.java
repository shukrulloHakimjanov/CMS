package com.uzum.cms.exception;

import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class CardExpiredException extends ApplicationException {
    public CardExpiredException(Error error) {
        super(error.getCode(), error.getMessage(), ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }
}
