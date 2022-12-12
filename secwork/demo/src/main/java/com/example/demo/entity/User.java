package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String userName;
    private String sex;
    private String PhoneNumber;
    private  int age;


    public User(int id, String userName, String sex, String phoneNumber, int age) {
        this.id = id;
        this.userName = userName;
        this.sex = sex;
        PhoneNumber = phoneNumber;
        this.age = age;
    }
}
