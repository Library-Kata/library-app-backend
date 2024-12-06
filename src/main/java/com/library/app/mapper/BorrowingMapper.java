package com.library.app.mapper;

import com.library.app.model.dto.BookDTO;
import com.library.app.model.dto.BorrowingDTO;
import com.library.app.model.dto.UserDTO;
import com.library.app.model.entity.BorrowingEntity;
import com.library.app.model.response.BorrowingResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BorrowingMapper {

    public BorrowingDTO toDto(BorrowingEntity entity) {
        UserDTO userDTO = UserDTO.builder()
                .username(entity.getUser().getUsername())
                .roles(entity.getUser().getRoles())
                .build();

        BookDTO bookDTO = BookDTO.builder()
                .id(entity.getBook().getId())
                .title(entity.getBook().getTitle())
                .author(entity.getBook().getAuthor())
                .available(entity.getBook().isAvailable())
                .build();

        return BorrowingDTO.builder()
                .id(entity.getId())
                .user(userDTO)
                .book(bookDTO)
                .borrowDate(entity.getBorrowDate())
                .returnedDate(entity.getReturnedDate())
                .build();
    }

    public BorrowingResponse toResponse(BorrowingDTO dto) {
        return BorrowingResponse.builder()
                .id(dto.getId())
                .user(UserMapper.toResponse(dto.getUser()))
                .book(BookMapper.toResponse(dto.getBook()))
                .borrowDate(dto.getBorrowDate())
                .returnedDate(dto.getReturnedDate())
                .build();
    }
}
