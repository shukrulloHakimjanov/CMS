package com.uzum.cms.service;

import com.uzum.cms.dto.response.AMSInfoResponse;

import java.util.UUID;

public interface AmsIntegrationService {

    AMSInfoResponse fetchAccountInfoById(final UUID accountId);
}
