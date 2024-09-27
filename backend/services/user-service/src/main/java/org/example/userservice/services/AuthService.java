package org.example.userservice.services;

import lombok.RequiredArgsConstructor;
import org.example.userservice.model.RegisteredUser;
import org.example.userservice.records.LoginUserRequest;
import org.example.userservice.records.RegisterUserRequest;
import org.example.userservice.repositories.RegisteredUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RegisteredUserService userService;
    private final AuthenticationManager authenticationManager;
    private final RegisteredUserRepository registeredUserRepository;

    public RegisteredUser registerUser(RegisterUserRequest request){
        return userService.register(request);
    }

    public RegisteredUser authenticate(LoginUserRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            ));

            RegisteredUser registeredUser = registeredUserRepository.findByUsername(request.username());

            if (registeredUser == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return registeredUser;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
