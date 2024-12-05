package com.library.app.repository;

import com.library.app.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for UserEntity.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
