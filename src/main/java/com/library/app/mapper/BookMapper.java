package com.library.app.mapper;

import com.library.app.model.dto.BookDTO;
import com.library.app.model.entity.BookEntity;
import com.library.app.model.request.BookRequest;
import com.library.app.model.response.BookResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookMapper {

    public BookDTO toDto(BookRequest request) {
        return BookDTO.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .build();
    }

    public BookResponse toResponse(BookDTO dto) {
        return BookResponse.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .available(dto.isAvailable())
                .build();
    }

    public BookDTO toDto(BookEntity entity) {
        return BookDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .available(entity.isAvailable())
                .build();
    }

    public BookEntity toEntity(BookDTO dto) {
        return BookEntity.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .available(dto.isAvailable())
                .build();
    }
}
