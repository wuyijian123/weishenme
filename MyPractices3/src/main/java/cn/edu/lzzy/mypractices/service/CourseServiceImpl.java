package cn.edu.lzzy.mypractices.service;

import cn.edu.lzzy.mypractices.constant.Messages;
import cn.edu.lzzy.mypractices.entity.Course;
import cn.edu.lzzy.mypractices.entity.User;
import cn.edu.lzzy.mypractices.repository.CourseRepository;
import cn.edu.lzzy.mypractices.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author lzzy on 2022/11/18.
 * Description:
 */
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    //依赖注入课程CourseRepository
    @Autowired
    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    //@Cacheable 可缓存
    @Cacheable(value = "courses", key = "'get'")
    @Override
    public List<Course> get() {
        return repository.findAllByOrderByUpdateTimeDesc();
    }

    @Cacheable(cacheNames = "pagedCourses", key = "#page+ '-' +#size")
    @Override
    public List<Course> get(int page, int size) {
        return repository.findAllByOrderByUpdateTimeDesc(PageRequest.of(page, size));
    }

    @Cacheable(value = "courseCount")
    @Override
    public Integer count() {
        return repository.countAllBy();
    }

    @Cacheable(value = "courseById", key = "#id")
    @Override
    public Course getById(UUID id) {
        //也可使用getById，但会延迟加载，当关联数据较多时可用
        return repository.findById(id).orElse(null);
    }
    //查询老师开设的课程
    @Cacheable(value = "teacherCourses", key = "#teacherId")
    @Override
    public List<Course> getTeachersCourses(UUID teacherId) {
        return repository.findCoursesByTeacherIdEqualsOrderByCreateTime(teacherId);
    }
    //查询学生的修读的课程
    @Cacheable(cacheNames = "studentCourses", key = "#studentId")
    @Override
    public List<Course> getStudentsCourses(UUID studentId) {
        User student = new User();
        student.setId(studentId);
        return repository.findCoursesByStudentsContainsAndOpenOrderByUpdateTime(student, true);
    }

    @Caching(put = {
            @CachePut(value = "courseById", key = "#course.id.toString()")
    }, evict = {
            @CacheEvict(value = "courses", key = "'get'"),
            @CacheEvict(value = "teacherCourses", key = "#course.teacher.id.toString()"),
            @CacheEvict(value = "pagedCourses", allEntries = true),
            @CacheEvict(value = "courseCount", allEntries = true),
            @CacheEvict(value = "studentCourses", allEntries = true)
    })
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Course add(Course course) {
        return repository.save(course);
    }

    @Caching(put = {
            @CachePut(value = "courseById", key = "#course.id.toString()")
    }, evict = {
            @CacheEvict(value = "courses", key = "'get'"),
            @CacheEvict(value = "teacherCourses", key = "#course.teacher.id.toString()"),
            @CacheEvict(value = "pagedCourses", allEntries = true),
            @CacheEvict(value = "studentCourses", allEntries = true)
    })
    //更新
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Course update(Course course, String token) {
        UUID teacherId = course.getTeacher().getId();
        //校验课程id和token
        if (AuthUtils.invalid(teacherId, token)) {
            return null;
        }
        course.setUpdateTime(new Date());
        return repository.save(course);
    }

    @Caching(evict = {
            @CacheEvict(value = "courseById", key = "#id"),
            @CacheEvict(value = "courses", key = "'get'"),
            @CacheEvict(value = "teacherCourses", allEntries = true),
            @CacheEvict(value = "pagedCourses", allEntries = true),
            @CacheEvict(value = "courseCount", allEntries = true),
            @CacheEvict(value = "studentCourses", allEntries = true)
    })

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Messages remove(UUID id, String token) {
        Course course = repository.findById(id).orElse(null);

        if (course == null){
            return  Messages.WRONG_ID;
        }
        UUID teacherId = course.getTeacher().getId();
        if (AuthUtils.invalid(teacherId, token)){
            return Messages.NO_PERMISSION;
        }

        repository.delete(course);
        return Messages.SUCCESS;
    }
}
