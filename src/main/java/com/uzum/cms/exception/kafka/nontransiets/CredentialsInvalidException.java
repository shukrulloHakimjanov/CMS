package com.uzum.cms.exception.kafka.nontransiets;

import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.ErrorType;

public class CredentialsInvalidException extends NonTransientException {
    public CredentialsInvalidException(Error error) {
        super(error.getCode(), error.getMessage(), ErrorType.INTERNAL, null);
    }
}
