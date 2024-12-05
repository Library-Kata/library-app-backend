package com.library.app.repository;

import com.library.app.model.entity.BorrowingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for BorrowingEntity.
 */
@Repository
public interface BorrowingRepository extends JpaRepository<BorrowingEntity, Long> {
    List<BorrowingEntity> findByUserUsername(String username);
    List<BorrowingEntity> findByUserUsernameAndReturnedDateIsNull(String username);
    boolean existsByUserUsernameAndBookIdAndReturnedDateIsNull(String username, Long bookId);
}
