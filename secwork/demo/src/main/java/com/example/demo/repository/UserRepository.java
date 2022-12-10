package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findById(int id);
    List<User> findAllByAge(int age);

    List<User> findAllByUserName(String userName);
}
