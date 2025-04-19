package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    // Поиск по дате, сумме и категории
    @Query("SELECT t FROM TransactionEntity t WHERE " +
            "(t.amount >= :minAmount OR :minAmount IS NULL) AND " +
            "(t.amount <= :maxAmount OR :maxAmount IS NULL) AND " +
            "(t.date BETWEEN :startDate AND :endDate OR :startDate IS NULL OR :endDate IS NULL) AND " +
            "(t.category = :category OR :category IS NULL)")
    List<TransactionEntity> findByFilters(
        Double minAmount,
        Double maxAmount,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String category
    );
}
