package com.uzum.cms.mapper;


import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.response.AMSInfoResponse;
import com.uzum.cms.dto.response.CardInfoResponse;
import com.uzum.cms.dto.response.CardResponse;
import com.uzum.cms.entity.CardEntity;
import com.uzum.cms.utility.UtilitiesService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cardNumber", source = "cardNumber")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "pin", source = "event.pinEncrypted")
    @Mapping(target = "cvv", source = "cvv")
    CardEntity toEntity(CardEmissionEvent event, String cardNumber, String cvv, String token, LocalDate expiryDate);

    CardResponse toDto(CardEntity entity);

    @Mapping(target = "pinEncrypted", source = "pinEncrypted")
    CardEmissionEvent requestToEvent(CardRequest request, String pinEncrypted);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "accountId", source = "amsInfoResponse.id")
    @Mapping(target = "accountStatus", source = "amsInfoResponse.status")
    @Mapping(target = "currency", source = "amsInfoResponse.currency")
    @Mapping(target = "cardExpireDate", source = "entity.expiryDate")
    CardInfoResponse entityToCardInfoResponse(CardEntity entity, AMSInfoResponse amsInfoResponse);
}
