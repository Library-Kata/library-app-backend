package com.library.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Entity representing a User in the database.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "username"))
    @Column(name = "role")
    private Set<String> roles;
}
