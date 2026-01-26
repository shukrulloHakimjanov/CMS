package com.uzum.cms.entity;

import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.constant.enums.Status;
import com.uzum.cms.constant.enums.UserType;
import com.uzum.cms.dto.response.CardDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Card extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    // Masked card number for display (e.g., **** **** **** 1234)
    @Column(name = "card_number_masked", nullable = false, length = 64)
    private String cardNumberMasked;

    @Column(name = "card_hash", nullable = false, length = 64, unique = true)
    private String cardHash;

    // Expiration date
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    // Card status: ACTIVE, BLOCKED, EXPIRED
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status;

    @Column(name = "account_number", nullable = false, unique = true)
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", length = 20)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", length = 20)
    private UserType userType;

    @Column(name = "token")
    private String token;
}
