package com.uzum.cms.controller;


import com.uzum.cms.constant.enums.Status;
import com.uzum.cms.dto.request.CreateCardRequest;
import com.uzum.cms.dto.response.CardDto;
import com.uzum.cms.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.uzum.cms.constant.Constant.CARD_API;

@RestController
@RequestMapping(CARD_API)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Card Management System", description = "APIs for managing user cards")
@Validated
public class CardController {

    private final CardService cardService;

    @Operation(summary = "Create a new card for a user")
    @PostMapping
    public ResponseEntity<CardDto> createCard(@Valid @RequestBody CreateCardRequest request) {
        CardDto createdCard = cardService.createCard(request);
        return ResponseEntity.ok(createdCard);
    }

    @Operation(summary = "Get card details by card ID")
    @GetMapping("/{cardId}")
    public ResponseEntity<CardDto> getCard(@PathVariable Long cardId) {
        CardDto card = cardService.getCardById(cardId);
        return ResponseEntity.ok(card);
    }

    @Operation(summary = "Update card status")
    @PatchMapping("/{cardId}/status")
    public ResponseEntity<CardDto> updateCardStatus(
            @PathVariable Long cardId,
            Status request
    ) {
        CardDto updatedCard = cardService.updateCardStatus(cardId, request);
        return ResponseEntity.ok(updatedCard);
    }

    @Operation(summary = "Get all cards for a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CardDto>> getCardsByUser(@PathVariable Long userId) {
        List<CardDto> cards = cardService.getCardByUserId(userId);
        return ResponseEntity.ok(cards);
    }
}

