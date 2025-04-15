package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.TransactionStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionStatusRepository extends JpaRepository<TransactionStatusEntity, UUID> {
}
