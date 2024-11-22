package org.example.userservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.userservice.model.RegisteredUser;
import org.example.userservice.model.Role;
import org.example.userservice.records.RegisterUserRequest;
import org.example.userservice.repositories.RegisteredUserRepository;
import org.example.userservice.repositories.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisteredUserService {

    private final RegisteredUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public RegisteredUser register(RegisterUserRequest request){
        RegisteredUser user = new RegisteredUser();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        Role userRole = roleRepository.findByName("ROLE_USER");
        if(userRole != null) {
            user.setRoles(List.of(userRole));
        }
        else {
            Role role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
            user.setRoles(List.of(role));
        }

        return userRepository.save(user);
    }
}
