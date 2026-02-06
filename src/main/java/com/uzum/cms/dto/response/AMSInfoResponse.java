package com.uzum.cms.dto.response;

import com.uzum.cms.constant.enums.AccountCurrency;
import com.uzum.cms.constant.enums.AccountStatus;
import com.uzum.cms.constant.enums.AccountType;

import java.util.UUID;

public record AMSInfoResponse(UUID id, UUID userId, String accountNumber, AccountStatus status,
                              AccountCurrency currency, AccountType accountType) {}
