package com.library.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a Book in the database.
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Builder.Default
    private boolean available = true;
}
