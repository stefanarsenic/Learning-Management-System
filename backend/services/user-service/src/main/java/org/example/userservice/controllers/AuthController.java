package org.example.userservice.controllers;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userservice.model.RegisteredUser;
import org.example.userservice.records.LoginResponse;
import org.example.userservice.records.LoginUserRequest;
import org.example.userservice.records.RegisterUserRequest;
import org.example.userservice.services.AuthService;
import org.example.userservice.services.JwtService;
import org.example.userservice.services.MyUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<RegisteredUser> registerUser(@RequestBody @Valid RegisterUserRequest request){
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginUserRequest request){
        try {
            RegisteredUser registeredUser = authService.authenticate(request);
            User user = (User) userDetailsService.loadUserByUsername(registeredUser.getUsername());
            String jwt = jwtService.generateToken(user);

            LoginResponse loginResponse = new LoginResponse(jwt, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        }
        catch (AuthenticationException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token){
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(false);
        }

        token = token.substring(7);

        try {
            String username = jwtService.extractUsername(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            Boolean isValid = jwtService.isTokenValid(token, userDetails);

            return ResponseEntity.ok(isValid);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}
