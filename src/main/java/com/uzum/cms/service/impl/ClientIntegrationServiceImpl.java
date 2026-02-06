package com.uzum.cms.service.impl;

import com.uzum.cms.dto.response.ClientInfoResponse;
import com.uzum.cms.service.ClientIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientIntegrationServiceImpl implements ClientIntegrationService {

    public ClientInfoResponse fetchClientInfoById(final UUID userId) {
        return null;
    }

}
