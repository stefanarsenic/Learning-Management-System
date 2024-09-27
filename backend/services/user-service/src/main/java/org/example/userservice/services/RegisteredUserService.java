package org.example.userservice.services;

import lombok.RequiredArgsConstructor;
import org.example.userservice.model.RegisteredUser;
import org.example.userservice.records.RegisterUserRequest;
import org.example.userservice.repositories.RegisteredUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisteredUserService {

    private final RegisteredUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisteredUser register(RegisterUserRequest request){
        RegisteredUser user = new RegisteredUser();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        return userRepository.save(user);
    }
}
