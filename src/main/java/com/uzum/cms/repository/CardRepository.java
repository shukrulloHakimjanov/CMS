package com.uzum.cms.repository;

import com.uzum.cms.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByUserId(Long userId);

    List<Card> findAllByUserId(Long userId);
}
