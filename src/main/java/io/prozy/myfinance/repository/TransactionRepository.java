package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.util.UUID;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    @Query("""
                SELECT t FROM TransactionEntity t
                JOIN t.categoryEntity c
                JOIN t.senderBankEntity s
                JOIN t.recipientBankEntity r
                JOIN t.transactionStatus ts
                JOIN t.transactionType tt
                WHERE (t.amount >= COALESCE(:minAmount, t.amount))
                  AND (t.amount <= COALESCE(:maxAmount, t.amount))
                  AND (t.transactionDateTime >= COALESCE(:startDate, t.transactionDateTime))
                  AND (t.transactionDateTime <= COALESCE(:endDate, t.transactionDateTime))
                  AND (c.id = COALESCE(:category, c.id))
                  AND (s.bankId = COALESCE(:senderId, s.bankId))
                  AND (r.bankId = COALESCE(:recipientId, r.bankId))
                  AND (ts.id = COALESCE(:transStatus, ts.id))
                  AND (tt.id = COALESCE(:transType, tt.id))
                  AND (:recipientInn IS NULL OR t.recipientInn LIKE CONCAT('%', :recipientInn, '%'))
            """)
    List<TransactionEntity> findByFilters(
            @org.springframework.data.repository.query.Param("minAmount") BigDecimal minAmount,
            @org.springframework.data.repository.query.Param("maxAmount") BigDecimal maxAmount,
            @org.springframework.data.repository.query.Param("startDate") LocalDateTime startDate,
            @org.springframework.data.repository.query.Param("endDate") LocalDateTime endDate,
            @org.springframework.data.repository.query.Param("category") UUID category,
            @org.springframework.data.repository.query.Param("senderId") UUID senderId,
            @org.springframework.data.repository.query.Param("recipientId") UUID recipientId,
            @org.springframework.data.repository.query.Param("transStatus") UUID transStatus,
            @org.springframework.data.repository.query.Param("transType") UUID transType,
            @org.springframework.data.repository.query.Param("recipientInn") String recipientInn
    );

}
