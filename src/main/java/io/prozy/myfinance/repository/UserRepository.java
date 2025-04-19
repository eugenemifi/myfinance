package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByLogin(String login);
}
