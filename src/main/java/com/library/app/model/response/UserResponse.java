package com.library.app.model.response;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class UserResponse {
    private String username;
    private Set<String> roles;
}
