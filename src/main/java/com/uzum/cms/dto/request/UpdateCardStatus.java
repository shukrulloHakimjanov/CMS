package com.uzum.cms.dto.request;

import com.uzum.cms.constant.enums.Status;
import jakarta.validation.constraints.NotNull;


public record UpdateCardStatus(@NotNull Status status) {}
