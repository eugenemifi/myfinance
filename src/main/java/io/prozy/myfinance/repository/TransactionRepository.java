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
                WHERE (t.amount >= COALESCE(:minAmount, t.amount))
                  AND (t.amount <= COALESCE(:maxAmount, t.amount))
                  AND (t.transactionDateTime >= COALESCE(:startDate, t.transactionDateTime))
                  AND (t.transactionDateTime <= COALESCE(:endDate, t.transactionDateTime))
                  AND (c.categoryName = COALESCE(:category, c.categoryName))
            """)
    List<TransactionEntity> findByFilters(
            @org.springframework.data.repository.query.Param("minAmount") BigDecimal minAmount,
            @org.springframework.data.repository.query.Param("maxAmount") BigDecimal maxAmount,
            @org.springframework.data.repository.query.Param("startDate") LocalDateTime startDate,
            @org.springframework.data.repository.query.Param("endDate") LocalDateTime endDate,
            @org.springframework.data.repository.query.Param("category") String category
    );

}
