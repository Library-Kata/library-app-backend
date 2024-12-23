package com.library.app.service.impl;

import com.library.app.exception.UsernameAlreadyTakenException;
import com.library.app.mapper.UserMapper;
import com.library.app.model.UserRole;
import com.library.app.model.dto.UserDTO;
import com.library.app.model.entity.BookEntity;
import com.library.app.model.entity.BorrowingEntity;
import com.library.app.model.entity.UserEntity;
import com.library.app.repository.BookRepository;
import com.library.app.repository.BorrowingRepository;
import com.library.app.repository.UserRepository;
import com.library.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of UserService.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;

    // Allowed roles as enums
    private final Set<UserRole> allowedRoles = Set.of(UserRole.ROLE_USER, UserRole.ROLE_ADMIN);

    @Override
    public void registerUser(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getUsername())) {
            throw new UsernameAlreadyTakenException("Username already taken");
        }

        // Check if all user roles are contained in allowedRoles
        if (!allowedRoles.containsAll(userDTO.getRoles())) {
            throw new IllegalArgumentException("One or more roles are invalid.");
        }

        var userEntity = UserMapper.toEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public void deleteUser(String targetUsername, String currentUsername) {
        if (targetUsername.equals(currentUsername)) {
            throw new RuntimeException("You cannot delete yourself.");
        }

        UserEntity targetUser = userRepository.findById(targetUsername)
                .orElseThrow(() -> new RuntimeException("User '" + targetUsername + "' does not exist."));

        if (targetUser.getRoles().contains(UserRole.ROLE_SUPERADMIN)) {
            throw new RuntimeException("You cannot delete a user with the SUPERADMIN role.");
        }

        // Fetch all borrowings of the user
        List<BorrowingEntity> borrowings = borrowingRepository.findByUserUsername(targetUsername);

        for (BorrowingEntity borrowing : borrowings) {
            // If the book is currently borrowed (not yet returned)
            if (borrowing.getReturnedDate() == null) {
                BookEntity book = borrowing.getBook();
                book.setAvailable(true); // Make the book available
                bookRepository.save(book);
            }
            borrowingRepository.delete(borrowing); // Delete the borrowing record
        }

        // Now, delete the user
        userRepository.delete(targetUser);
    }
}
