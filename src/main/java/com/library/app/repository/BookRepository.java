package com.library.app.repository;

import com.library.app.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for BookEntity.
 */
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
