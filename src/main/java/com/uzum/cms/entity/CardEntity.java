package com.uzum.cms.entity;

import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.constant.enums.Status;
import com.uzum.cms.constant.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CardEntity extends BaseEntity {

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "card_number", nullable = false, length = 64)
    private String cardNumber;

    @Column(name = "token", nullable = false, unique = true, length = 64)
    private String token;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", length = 20)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", length = 20)
    private UserType userType;

    @Column(name = "pin", length = 4)
    private String pin;

    @Column(name = "ccv", length = 4)
    private String ccv;

    @Column(name = "holder_name", nullable = false, length = 100)
    private String holderName;
}
