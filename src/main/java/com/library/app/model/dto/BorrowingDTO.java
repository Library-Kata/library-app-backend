package com.library.app.model.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Borrowing.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowingDTO {
    private Long id;
    private UserDTO user;
    private BookDTO book;
    private LocalDateTime borrowDate;
    private LocalDateTime returnedDate;
}
