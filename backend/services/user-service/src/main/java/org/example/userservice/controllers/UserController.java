package org.example.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.userservice.records.LoginResponse;
import org.example.userservice.services.JwtService;
import org.example.userservice.services.MyUserDetailsService;
import org.example.userservice.services.RegisteredUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final MyUserDetailsService userDetailsService;

    @GetMapping("/by-username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        User user = (User) userDetailsService.loadUserByUsername(username);
        if(user != null){
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }
}
