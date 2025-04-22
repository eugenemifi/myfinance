package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("""
                  SELECT t FROM TransactionEntity t WHERE
                  t.amount >= :minAmount AND 
                  t.amount <= :maxAmount AND
                  t.transactionDateTime >= :startDate AND 
                  t.transactionDateTime <= :endDate AND
                  t.categoryEntity.categoryName = :category
            """)
    List<TransactionEntity> findByFilters(
            Double minAmount,
            Double maxAmount,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String category);
}
