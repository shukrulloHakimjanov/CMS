package com.uzum.cms.exception.http;

import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.ErrorType;
import com.uzum.cms.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class HttpServerException extends ApplicationException {

    public HttpServerException(String message, HttpStatusCode status) {
        super(Error.HTTP_SERVICE_ERROR_CODE.getCode(), message, ErrorType.EXTERNAL, HttpStatus.valueOf(status.value()));
    }
}
