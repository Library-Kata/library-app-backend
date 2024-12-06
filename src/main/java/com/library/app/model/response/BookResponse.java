package com.library.app.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private boolean available;
}
