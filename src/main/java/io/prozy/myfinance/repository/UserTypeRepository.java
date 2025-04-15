package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTypeRepository extends JpaRepository<UserTypeEntity, UUID> {
}
