package com.uzum.cms.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UtilitiesService {
    private final SecureRandom secureRandom;

    public String generateCardNumber() {
        String prefix = "0820";
        StringBuilder sb = new StringBuilder(prefix);

        for (int i = 0; i < 12; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    private String maskCardNumber(String cardNumber) {
        return "**** **** **** " + cardNumber.substring(12);
    }

    public String generateToken(String cardNumber) {
        return UUID.nameUUIDFromBytes(cardNumber.getBytes()).toString();
    }

    public String generateCvv() {
        int cvv = secureRandom.nextInt(900) + 100;
        return String.valueOf(cvv);
    }


    public LocalDate generateExpiryDate() {
        return LocalDate.now().plusYears(10);
    }

}
