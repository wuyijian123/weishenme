package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@CrossOrigin(origins = "http://127.0.0.1:5500/")
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUser(@RequestParam(required = false) String userName) {
        try {
            List<User> users = new ArrayList<User>();
            if (userName == null) {
                userRepository.findAll().forEach(users::add);
            } else {
                users.addAll(userRepository.findAllByUserName(userName));
            }
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id")int id){
       User user=userRepository.findById(id);
        if (user!=null){
            return  new ResponseEntity<>(user,HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/user")
    public  ResponseEntity<User> saveUser(@RequestBody User user){
        try{
            User user1=userRepository.save(new User(user.getId(), user.getUserName(), user.getSex(), user.getPhoneNumber(), user.getAge()));
            return new ResponseEntity<>(user1,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/user/{id}")
    public  ResponseEntity<User> updateUser(@PathVariable("id") long id,@RequestBody User user){
        Optional<User> userdata=userRepository.findById(id);
        if(userdata.isPresent()){
            User user1=userdata.get();
            user1.setUserName(user.getUserName());
            user1.setAge(user.getAge());
            user1.setPhoneNumber(user.getPhoneNumber());
            user1.setSex(user.getSex());
            return new ResponseEntity<>(userRepository.save(user1),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/user")
    public ResponseEntity<HttpStatus> deleteUserALl() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
