package com.uzum.cms.exception.kafka.nontransiets.account;

import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.ErrorType;
import com.uzum.cms.exception.kafka.nontransiets.NonTransientException;

public class AccountNotActiveException extends NonTransientException {

    public AccountNotActiveException(Error error) {
        super(error.getCode(), error.getMessage(), ErrorType.EXTERNAL, null);
    }
}
