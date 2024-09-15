package org.example.userservice.repository;

import org.example.userservice.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {

    RegisteredUser findByUsername(String username);
}