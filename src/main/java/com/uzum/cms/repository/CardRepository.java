package com.uzum.cms.repository;

import com.uzum.cms.entity.CardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByUserId(Long userId);

    Optional<CardEntity> findByToken(String token);

    Page<CardEntity> findAllByUserId(Long userId, Pageable pageable);
}
