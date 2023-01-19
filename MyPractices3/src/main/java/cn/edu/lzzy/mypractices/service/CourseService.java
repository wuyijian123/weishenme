package cn.edu.lzzy.mypractices.service;

import cn.edu.lzzy.mypractices.constant.Messages;
import cn.edu.lzzy.mypractices.entity.Course;

import java.util.List;
import java.util.UUID;

/**
 * @author lzzy on 2022/11/18.
 * Description:
 */
public interface CourseService {
    List<Course> get();
    List<Course> get(int page, int size);
    Integer count();
    Course getById(UUID id);
    List<Course> getTeachersCourses(UUID teacherId);
    List<Course> getStudentsCourses(UUID studentId);
    Course add(Course course);
    Course update(Course course, String token);
    Messages remove(UUID id, String token);
}
