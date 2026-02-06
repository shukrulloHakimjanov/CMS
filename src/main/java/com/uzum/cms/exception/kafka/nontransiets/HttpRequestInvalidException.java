package com.uzum.cms.exception.kafka.nontransiets;

import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.ErrorType;

public class HttpRequestInvalidException extends NonTransientException {
    public HttpRequestInvalidException(Error error, Exception ex) {
        super(error.getCode(), ex.getMessage(), ErrorType.INTERNAL, null);
    }
}
