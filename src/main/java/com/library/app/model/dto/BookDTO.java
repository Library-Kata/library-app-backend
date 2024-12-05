package com.library.app.model.dto;

import lombok.*;

/**
 * Data Transfer Object for Book.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private boolean available;
}
