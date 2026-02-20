package com.uzum.cms.dto.response;

import com.uzum.cms.constant.enums.AccountCurrency;
import com.uzum.cms.constant.enums.AccountStatus;

import java.time.LocalDate;
import java.util.UUID;

public record CardInfoResponse(Long id, UUID accountId, AccountStatus accountStatus, AccountCurrency currency,
                               LocalDate cardExpireDate) {}
