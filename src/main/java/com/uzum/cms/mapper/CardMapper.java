package com.uzum.cms.mapper;


import com.uzum.cms.dto.request.CreateCardRequest;
import com.uzum.cms.dto.response.CardDto;
import com.uzum.cms.entity.Card;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto toDto(Card card);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cardHash", ignore = true)
    @Mapping(target="cardNumberMasked",ignore = true)
    @Mapping(target = "status", ignore = true)
    Card toEntity(CreateCardRequest request);

}
