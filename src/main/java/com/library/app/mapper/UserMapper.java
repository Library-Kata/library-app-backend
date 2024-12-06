package com.library.app.mapper;

import com.library.app.model.UserRole;
import com.library.app.model.dto.UserDTO;
import com.library.app.model.entity.UserEntity;
import com.library.app.model.request.UserRequest;
import com.library.app.model.response.UserResponse;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public UserDTO toDto(UserRequest request) {
        // Convert Set<String> to Set<UserRole>
        Set<UserRole> userRoles = request.getRoles().stream()
                .map(UserRole::valueOf) // Convert string like "ROLE_USER" to UserRole. Make sure the strings match exactly.
                .collect(Collectors.toSet());

        return UserDTO.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .roles(userRoles)
                .build();
    }

    public UserResponse toResponse(UserDTO dto) {
        // Convert Set<UserRole> to Set<String>
        Set<String> rolesAsString = dto.getRoles().stream()
                .map(UserRole::name) // Convert enum to its name (e.g. ROLE_USER -> "ROLE_USER")
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .username(dto.getUsername())
                .roles(rolesAsString)
                .build();
    }

    public UserDTO toDto(UserEntity entity) {
        return UserDTO.builder()
                .username(entity.getUsername())
                .roles(entity.getRoles()) // Already Set<UserRole>
                .build();
    }

    public UserEntity toEntity(UserDTO dto) {
        return UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(dto.getRoles()) // Already Set<UserRole>
                .build();
    }
}
