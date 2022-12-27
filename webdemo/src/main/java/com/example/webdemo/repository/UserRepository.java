package com.example.webdemo.repository;

import com.example.webdemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
   Optional<User> findById(UUID id);
   List<User> findAll();
}
