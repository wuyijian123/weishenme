package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {
        User user=new User(1L,"小红","女","114514",15);
        userRepository.save(user);
    }



}
