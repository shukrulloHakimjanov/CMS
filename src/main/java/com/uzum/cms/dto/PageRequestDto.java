package com.uzum.cms.dto;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public class PageRequestDto {

    private int page = 0;
    private int size = 10;

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
