package com.uzum.cms.mapper;


import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.response.CardResponse;
import com.uzum.cms.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cardNumber", ignore = true)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "expiryDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "ccv", ignore = true)
    CardEntity toEntity(CardRequest request);

    CardResponse toDto(CardEntity entity);
}
