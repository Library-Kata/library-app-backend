package com.library.app.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BorrowingResponse {
    private Long id;
    private UserResponse user; // nested user response
    private BookResponse book; // nested book response
    private LocalDateTime borrowDate;
    private LocalDateTime returnedDate;
}
