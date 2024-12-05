package com.library.app.service;

import com.library.app.model.dto.UserDTO;

import java.util.List;

/**
 * Service interface for user-related operations.
 */
public interface UserService {
    void registerUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    void deleteUser(String username, String currentUsername);
}
