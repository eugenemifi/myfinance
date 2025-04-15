package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankRepository extends JpaRepository<BankEntity, UUID> {
}
