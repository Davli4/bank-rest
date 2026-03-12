package com.example.bankcards.repository;

import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {
    Page<Card> findByUserId(Long userId, Pageable pageable);

    Optional<Card> findByIdAndUserId(Long cardId, Long userId);

    List<Card> findByUserIdAndStatus(Long userId, CardStatus status);

    boolean existsByCardNumber(String cardName);

    @Query("SELECT c FROM Card c WHERE " +
            "(:userId IS NULL OR c.user.id = :userId) AND " +
            "(:status IS NULL OR c.status = :status) AND " +
            "(:owner IS NULL OR LOWER(c.owner) LIKE LOWER(CONCAT('%', :owner, '%')))")
    Page<Card> findWithFilters(
            @Param("userId") Long userId,
            @Param("status") CardStatus status,
            @Param("owner") String owner,
            Pageable pageable);
}
