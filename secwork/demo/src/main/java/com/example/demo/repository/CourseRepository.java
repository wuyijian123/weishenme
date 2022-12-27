package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CourseRepository  extends JpaRepository<Course,Long> {
    List<Course> findCoursesByUsersId(Long userid);
}
