package com.library.app.model.dto;

import com.library.app.model.UserRole;
import lombok.*;

import java.util.Set;

/**
 * Data Transfer Object for User.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String password;
    private Set<UserRole> roles;
}
