package com.uzum.cms.dto.event;

import com.uzum.cms.constant.enums.CardStatusCode;
import com.uzum.cms.dto.response.CardResponse;

public record CardTerminalStateEvent(CardStatusCode code,
                                     CardEmissionEvent emissionData,
                                     CardResponse card,
                                     String errorMessage) {
}
