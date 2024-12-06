package com.library.app.model.request;

import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {
    private String username;
    private String password;
    private Set<String> roles;
}
