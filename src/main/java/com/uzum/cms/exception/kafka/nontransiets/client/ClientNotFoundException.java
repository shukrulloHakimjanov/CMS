package com.uzum.cms.exception.kafka.nontransiets.client;

import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.ErrorType;
import com.uzum.cms.exception.kafka.nontransiets.NonTransientException;

public class ClientNotFoundException extends NonTransientException {
    public ClientNotFoundException(Error error) {
        super(error.getCode(), error.getMessage(), ErrorType.EXTERNAL, null);
    }
}
