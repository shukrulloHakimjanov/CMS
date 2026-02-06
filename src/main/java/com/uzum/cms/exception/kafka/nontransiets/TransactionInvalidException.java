package com.uzum.cms.exception.kafka.nontransiets;

import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.ErrorType;

public class TransactionInvalidException extends NonTransientException {
    public TransactionInvalidException(Error error) {
        super(error.getCode(), error.getMessage(), ErrorType.INTERNAL, null);
    }
}
