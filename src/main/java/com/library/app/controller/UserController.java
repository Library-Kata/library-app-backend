package com.library.app.controller;

import com.library.app.mapper.UserMapper;
import com.library.app.model.request.UserRequest;
import com.library.app.model.response.UserResponse;
import com.library.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve the list of all users.")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        var users = userService.getAllUsers().stream()
                .map(UserMapper::toResponse)
                .toList();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Delete a user", description = "Delete a user by their username.")
    public ResponseEntity<String> deleteUser(
            @PathVariable String username,
            Authentication authentication) {
        try {
            userService.deleteUser(username, authentication.getName());
            return ResponseEntity.ok("User deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Moved registration to AuthController to keep things consistent
}
