package org.example.userservice.records;

import java.util.List;

public record LoginResponse(
        String username,
        List<String> roles,
        String token,
        long expiresIn
) {
}
