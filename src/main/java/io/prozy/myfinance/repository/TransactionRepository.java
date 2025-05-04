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
                WHERE (:minAmount IS NULL OR t.amount >= :minAmount)
                  AND (:maxAmount IS NULL OR t.amount <= :maxAmount)
                  AND (:startDate IS NULL OR t.transactionDateTime >= :startDate)
                  AND (:endDate IS NULL OR t.transactionDateTime <= :endDate)
                  AND (:category IS NULL OR t.categoryEntity.categoryName = :category)
            """)
    List<TransactionEntity> findByFilters(
            Double minAmount,
            Double maxAmount,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String category);

}
