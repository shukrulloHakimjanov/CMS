package com.uzum.cms.mapper;


import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.response.CardResponse;
import com.uzum.cms.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardResponse toDto(Card card);

    @Mapping(target = "id", ignore = true)
    Card toEntity(CardRequest request);

}
