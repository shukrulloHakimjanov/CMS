package com.uzum.cms.controller;

import com.uzum.cms.dto.PageRequestDto;
import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.request.UpdateCardStatus;
import com.uzum.cms.dto.response.CardResponse;
import com.uzum.cms.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.uzum.cms.constant.Constant.CARD_API;

@RestController
@RequestMapping(CARD_API)
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Card Management System", description = "APIs for managing user cards")
public class CardController {

    private final CardService cardService;

    @Operation(summary = "Create a new card for a user")
    @PostMapping
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest request) {
        log.info("Create card request received");
        return ResponseEntity.ok(cardService.createCard(request));
    }

    @Operation(summary = "Get card details by card ID")
    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getCardById(cardId));
    }

    @Operation(summary = "Update card status")
    @PatchMapping("/{cardId}/status")
    public ResponseEntity<CardResponse> updateCardStatus(@PathVariable Long cardId, @Valid @RequestBody UpdateCardStatus request) {
        return ResponseEntity.ok(cardService.updateCardStatus(cardId, request));
    }

    @Operation(summary = "Get all cards for a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<CardResponse>> getCardsByUserId(
            @PathVariable Long userId,
            PageRequestDto pageRequest
    ) {
        return ResponseEntity.ok(cardService.getCardsByUserId(userId, pageRequest));
    }

}
