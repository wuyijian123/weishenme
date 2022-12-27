package com.example.webdemo.controller;

import com.example.webdemo.entities.User;
import com.example.webdemo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsersList() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @PostMapping("/user")
    public  ResponseEntity<User> saveUser(@RequestBody User user){
        System.out.println(1);
        User user1=userRepository.save(new User(UUID.randomUUID(),user.getNickName(),user.getUserName(),user.getEmail(),user.getPhone(),user.getPassword(),user.getAvatar(),2,user.getSalt(),user.isApplyTeacher()));
        return new ResponseEntity<>(user1,HttpStatus.CREATED);
    }


}
