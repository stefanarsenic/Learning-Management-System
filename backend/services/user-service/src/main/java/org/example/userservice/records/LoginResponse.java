package org.example.userservice.records;

public record LoginResponse(
        String username,
        String token,
        long expiresIn
) {
}
