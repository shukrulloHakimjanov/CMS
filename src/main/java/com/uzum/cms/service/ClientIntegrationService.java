package com.uzum.cms.service;

import com.uzum.cms.dto.response.ClientInfoResponse;

import java.util.UUID;

public interface ClientIntegrationService {

    ClientInfoResponse fetchClientInfoById(final UUID userId);
}
