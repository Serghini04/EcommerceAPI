package com.serghini.store.repositories;

import com.serghini.store.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);
    public Optional<User> findFirstByEmail(String email);
}
