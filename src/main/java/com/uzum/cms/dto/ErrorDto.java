package com.uzum.cms.dto;

import com.uzum.cms.constant.enums.ErrorType;
import lombok.Builder;

import java.util.List;

@Builder
public record ErrorDto(
        int code,
        String message,
        ErrorType type,
        List<String> validationErrors) {}
