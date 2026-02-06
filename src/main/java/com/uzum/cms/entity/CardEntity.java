package com.uzum.cms.entity;

import com.uzum.cms.constant.enums.CardNetworkType;
import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.constant.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class CardEntity extends BaseEntity {

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "card_number", nullable = false, length = 64)
    private String cardNumber;

    @Column(name = "token", nullable = false, unique = true, length = 64)
    private String token;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "card_network_type", nullable = false)
    private CardNetworkType cardNetworkType;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "card_type", nullable = false)
    private CardType cardType;

    @Column(name = "pin", length = 4, nullable = false)
    private String pin;

    @Column(name = "cvv", length = 4, nullable = false)
    private String cvv;

    @Column(name = "holder_name", nullable = false, length = 100)
    private String holderName;
}
