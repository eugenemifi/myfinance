package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.TransactionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionTypeRepository extends JpaRepository<TransactionTypeEntity, UUID> {
}
