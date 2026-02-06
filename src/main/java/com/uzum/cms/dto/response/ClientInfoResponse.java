package com.uzum.cms.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ClientInfoResponse(UUID id, String firstName, String lastName, boolean isActive,
                                 OffsetDateTime createdAt) {}
