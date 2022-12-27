package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
   Optional<User> findById(long id);
    List<User> findAllByAge(long age);

    List<User> findAllByUserName(String userName);
}
