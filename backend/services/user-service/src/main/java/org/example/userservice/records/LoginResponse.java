package org.example.userservice.records;

public record LoginResponse(
        String token,
        long expiresIn
) {
}
