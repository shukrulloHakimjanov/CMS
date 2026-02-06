package com.uzum.cms.service.impl;

import com.uzum.cms.dto.response.AMSInfoResponse;
import com.uzum.cms.service.AmsIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmsIntegrationServiceImpl implements AmsIntegrationService {

    public AMSInfoResponse fetchAccountInfoById(final UUID accountId) {
        return null;
    }

}
