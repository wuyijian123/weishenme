package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String userName;
    private String sex;
    private String PhoneNumber;
    private  int age;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "users_courses",
            joinColumns = { @JoinColumn(name = "users_id") },
            inverseJoinColumns = { @JoinColumn(name = "courses_id") })
    private Set<Course> courses =new HashSet<>();


    public User(Long id, String userName, String sex, String phoneNumber, int age) {
        this.id = id;
        this.userName = userName;
        this.sex = sex;
        PhoneNumber = phoneNumber;
        this.age = age;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getUsers().add(this);
    }

    public void removeTag(Long courseId) {
        Course course=this.courses.stream().filter(t->t.getId()==courseId).findFirst().orElse(null);
        if (course!=null){
            this.courses.remove(course);
            course.getUsers().remove(this);
        }
    }
}
