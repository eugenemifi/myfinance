package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, UUID> {
  Page<BankEntity> findAll(Pageable pageable);
}
