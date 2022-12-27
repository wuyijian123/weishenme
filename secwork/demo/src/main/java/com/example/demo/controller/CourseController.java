package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://127.0.0.1:5500/")
@RestController
@RequestMapping("/api")
public class CourseController {
    private  final UserRepository userRepository;
    private  final CourseRepository courseRepository;

    public CourseController(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }
    @GetMapping("/course")
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> courses=new ArrayList<>();
        courseRepository.findAll().forEach(courses::add);
        if(courses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(courses,HttpStatus.OK);
    }
    @PostMapping("/user/{userId}/courses")
    public  ResponseEntity<Course> addCourse(@PathVariable Long userId,@RequestBody Course course){
        Course course1=userRepository.findById(userId).map(user -> {
            long courseId=course.getId();
            if (courseId != 0L) {
                Course course2 = courseRepository.findById(courseId)
                        .orElseThrow(() -> new ResourceAccessException("Not found Tag with id = " + courseId));
                user.addCourse(course2);
                userRepository.save(user);
//                tutorialRepository.save(tutorial);
                return course2;
            }
            user.addCourse(course);
            return courseRepository.save(course);
        }).orElseThrow(() ->new ResourceAccessException("Not found Tag with id = " + userId));
        return new ResponseEntity<>(course,HttpStatus.OK);
    }
    @PutMapping("/course/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") long id,@RequestBody Course course){
        Course course1=courseRepository.findById(id)
                .orElseThrow(()->new ResourceAccessException("courseId"+id+"NOT FOUND"));
        course1.setCourseName(course.getCourseName());
        return new ResponseEntity<>(courseRepository.save(course1),HttpStatus.OK);
    }
    @DeleteMapping("/users/{userId}/course/{courseId}")
    public ResponseEntity<HttpStatus> deleteCourseFromUser(@PathVariable(value = "userId") Long userId, @PathVariable(value = "courseId") Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceAccessException("Not found Tutorial with id = " + userId));

        user.removeTag(courseId);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
       courseRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
