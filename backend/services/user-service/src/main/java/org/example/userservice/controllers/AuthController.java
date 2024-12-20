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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("Error", "Authorization header missing");
            return ResponseEntity.badRequest().body(errorMessage);
        }

        token = token.substring(7);

        try {
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            boolean isValid = jwtService.isTokenValid(token, userDetails);

            if(isValid) {
                LoginResponse loginResponse = new LoginResponse(username, List.of(userDetails.getAuthorities().toString()), token, jwtService.getExpirationTime());
                return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
            }
            else {
                HashMap<String, String> errorMessage = new HashMap<>();
                errorMessage.put("Error", "Token validation failed");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
            }
        } catch (UsernameNotFoundException ex) {
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("Error", "Username not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        } catch (Exception ex) {
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("Error", "Token validation failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest request){
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserRequest request){
        try {
            RegisteredUser registeredUser = authService.authenticate(request);
            User user = (User) userDetailsService.loadUserByUsername(registeredUser.getUsername());
            String jwt = jwtService.generateToken(user);

            LoginResponse loginResponse = new LoginResponse(user.getUsername(), List.of(user.getAuthorities().toString()), jwt, jwtService.getExpirationTime());
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
