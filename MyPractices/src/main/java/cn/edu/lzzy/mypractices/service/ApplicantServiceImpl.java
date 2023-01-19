package cn.edu.lzzy.mypractices.service;

import cn.edu.lzzy.mypractices.entity.*;

import  cn.edu.lzzy.mypractices.*;
import cn.edu.lzzy.mypractices.repository.ApplicantRepository;
import cn.edu.lzzy.mypractices.repository.CourseRepository;
import cn.edu.lzzy.mypractices.repository.UserRepository;
import cn.edu.lzzy.mypractices.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author lzzy on 2022/11/21.
 * Description:
 */
@Service
public class ApplicantServiceImpl implements ApplicantService {
    private final ApplicantRepository repository;
    private final CourseRepository cRepository;
    private final UserRepository uRepository;
    //依赖注入申请、课程、用户（教师、学生）repository
    @Autowired
    public ApplicantServiceImpl(ApplicantRepository repository, CourseRepository cRepository, UserRepository uRepository){
        this.repository = repository;
        this.cRepository = cRepository;
        this.uRepository = uRepository;
    }
    //学生申请课程
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Applicant apply(UUID studentId, UUID courseId, String token) {
        //token校验学生
        if (AuthUtils.invalid(studentId, token)){
            return null;
        }
        //查找学生
        User student = uRepository.findById(studentId).orElse(null);
        if (student == null){
            return null;
        }
        //查找课程
        Course course = cRepository.findById(courseId).orElse(null);
        if (course == null){
            return null;
        }
        //申请记录存入数据库
        Applicant applicant = new Applicant();
        applicant.setCourse(course);
        applicant.setStudent(student);
        applicant.setStatus(Applicant.STATUS_APPLYING);
        return repository.save(applicant);
    }
    //查询某个学生的申请列表
    @Override
    public List<Applicant> getAllCourses(UUID studentId, String token) {
        //token校验
        if (AuthUtils.invalid(studentId, token)){
            return null;
        }
        //查询某个学生的申请列表
        return repository.findApplicantsByStudentIdEqualsOrderByApplyTimeDesc(studentId);
    }
    //查询某个学生已经选修的课程
    @Override
    public List<Applicant> getEnrolledCourses(UUID studentId, String token) {
        if (AuthUtils.invalid(studentId, token)){
            return null;
        }
        return repository.findApplicantsByStudentIdEqualsAndStatusEqualsOrderByApplyTimeDesc(studentId, Applicant.STATUS_APPROVED);
    }
    //查询某个学生正在申请的课程
    @Override
    public List<Applicant> getApplyingCourses(UUID studentId, String token) {
        if (AuthUtils.invalid(studentId, token)){
            return null;
        }
        return repository.findApplicantsByStudentIdEqualsAndStatusEqualsOrderByApplyTimeDesc(studentId, Applicant.STATUS_APPLYING);
    }
    //查询某个学生被拒绝的课程
    @Override
    public List<Applicant> getDeclinedCourses(UUID studentId, String token) {
        if (AuthUtils.invalid(studentId, token)){
            return null;
        }
        return repository.findApplicantsByStudentIdEqualsAndStatusEqualsOrderByApplyTimeDesc(studentId, Applicant.STATUS_DECLINED);
    }
    //教师更改课程申请状态
    private Applicant update(UUID applicantId, String token, int status){
        Applicant applicant = repository.findById(applicantId).orElse(null);
        if (applicant == null){
            return null;
        }
        Course course = applicant.getCourse();
        UUID teacherId = course.getTeacher().getId();
        if (AuthUtils.invalid(teacherId, token)){
            return null;
        }
        applicant.setStatus(status);
        return repository.save(applicant);
    }
    //教师同意申请课程
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Applicant approve(UUID applicantId, String token) {
        Applicant applicant = update(applicantId, token, Applicant.STATUS_APPROVED);
        if (applicant != null){
            Course course = applicant.getCourse();
            User student = applicant.getStudent();
            List<User> students = course.getStudents();
            if (!students.contains(student)){
                students.add(student);
            }
            course.setStudents(students);
            applicant.setCourse(cRepository.save(course));
        }
        return applicant;
    }
   //教师拒绝某个学生的申请
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Applicant decline(UUID applicantId, String token) {
        return update(applicantId, token, Applicant.STATUS_DECLINED);
    }
    //教师查询某门课程已选修列表
    @Override
    public List<Applicant> getEnrolledStudents(UUID courseId, String token) {
        Course course = cRepository.findById(courseId).orElse(null);
        if (course == null){
            return null;
        }
        UUID teacherId = course.getTeacher().getId();
        if (AuthUtils.invalid(teacherId, token)){
            return null;
        }
        return repository.findApplicantsByCourseIdEqualsAndStatusEqualsOrderByApplyTimeAsc(courseId, Applicant.STATUS_APPROVED);
    }
    //教师查询某个门课程申请的学生列表
    @Override
    public List<Applicant> getApplyingStudents(UUID courseId, String token) {
        Course course = cRepository.findById(courseId).orElse(null);
        if (course == null){
            return null;
        }
        UUID teacherId = course.getTeacher().getId();
        if (AuthUtils.invalid(teacherId, token)){
            return null;
        }
        return repository.findApplicantsByCourseIdEqualsAndStatusEqualsOrderByApplyTimeAsc(courseId, Applicant.STATUS_APPLYING);
    }
    //教师查询某门课程拒绝的学生列表
    @Override
    public List<Applicant> getDeclinedStudents(UUID courseId, String token) {
        Course course = cRepository.findById(courseId).orElse(null);
        if (course == null){
            return null;
        }
        UUID teacherId = course.getTeacher().getId();
        if (AuthUtils.invalid(teacherId, token)){
            return null;
        }
        return repository.findApplicantsByCourseIdEqualsAndStatusEqualsOrderByApplyTimeAsc(courseId, Applicant.STATUS_DECLINED);
    }
    //教师移除某门课程的学生
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Course removeStudent(UUID courseId, UUID studentId, String token) {
        Course course = cRepository.findById(courseId).orElse(null);
        if (course == null){
            return null;
        }
        UUID teacherId = course.getTeacher().getId();
        if (AuthUtils.invalid(teacherId, token)){
            return null;
        }
        course.getStudents().removeIf(s -> s.getId().equals(studentId));
        Applicant applicant = repository.findApplicantByCourseIdAndStudentId(courseId, studentId);
        if (applicant != null){
            update(applicant.getId(), token, Applicant.STATUS_DECLINED);
        }
        return cRepository.save(course);
    }
}
