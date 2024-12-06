package com.library.app.controller;

import com.library.app.model.request.LoginRequest;
import com.library.app.model.request.UserRequest;
import com.library.app.model.response.LoginResponse;
import com.library.app.security.JwtTokenUtil;
import com.library.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for authentication and authorization")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user by providing their details.")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        try {
            // Convert UserRequest to UserDTO inside userService or using mapper directly
            var dto = com.library.app.mapper.UserMapper.toDto(userRequest);
            userService.registerUser(dto);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Login by providing valid credentials and receive a JWT token.")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()
                    )
            );

            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenUtil.generateToken(authentication.getName(), roles);
            return ResponseEntity.ok(LoginResponse.builder().token(token).build());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
