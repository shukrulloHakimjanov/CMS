package com.uzum.cms.exception.kafka.transients;

import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.ErrorType;
import com.uzum.cms.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class TransientException extends ApplicationException {

    public TransientException(Exception ex) {
        super(Error.INTERNAL_SERVICE_ERROR_CODE.getCode(), ex.getMessage(), ErrorType.INTERNAL, null);
    }


    public TransientException(int code, String message, ErrorType errorType, HttpStatus status) {
        super(code, message, errorType, status);
    }

}
