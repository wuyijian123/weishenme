package cn.edu.lzzy.mypractices.repository;

import cn.edu.lzzy.mypractices.entity.Course;
import cn.edu.lzzy.mypractices.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author lzzy on 2022/11/18.
 * Description:
 */
public interface CourseRepository extends JpaRepository<Course, UUID> {
    //获取所有课程并以更新时间排序
    List<Course> findAllByOrderByUpdateTimeDesc();
    //分页获取所有课程
    List<Course> findAllByOrderByUpdateTimeDesc(Pageable pageable);
    //获取课程记录数
    Integer countAllBy();
    //查找某位教师（ID）创建的所有课程
    List<Course> findCoursesByTeacherIdEqualsOrderByCreateTime(UUID id);
    //查找某位教师（user）创建的所有课程
    List<Course> findCoursesByTeacherEqualsOrderByCreateTime(User teacher);
    //学生获得开放课程
    List<Course> findCoursesByStudentsContainsAndOpenOrderByUpdateTime(User student, boolean isOpen);

}
