package org.example.userservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userservice.model.RegisteredUser;
import org.example.userservice.records.LoginResponse;
import org.example.userservice.records.LoginUserRequest;
import org.example.userservice.records.RegisterUserRequest;
import org.example.userservice.services.AuthService;
import org.example.userservice.services.JwtService;
import org.example.userservice.services.MyUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserRequest request){
        RegisteredUser registeredUser = authService.authenticate(request);
        User user = (User) userDetailsService.loadUserByUsername(registeredUser.getUsername());
        String jwt =  jwtService.generateToken(user);

        LoginResponse loginResponse = new LoginResponse(jwt, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
