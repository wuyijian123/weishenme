package cn.edu.lzzy.mypractices.service;

import cn.edu.lzzy.mypractices.entity.Applicant;
import cn.edu.lzzy.mypractices.entity.Course;

import java.util.List;
import java.util.UUID;

/**
 * @author lzzy on 2021/11/20.
 * Description:
 */
public interface ApplicantService {
    //学生角色
    //学生用户申请选修某一课程
    Applicant apply(UUID studentId, UUID courseId, String token);
//    获取学生的所有状态的课程，包括申请中的、通过的和已拒绝的
    List<Applicant> getAllCourses(UUID studentId, String token);
//    获取已选修并审批通过的课程
    List<Applicant> getEnrolledCourses(UUID studentId, String token);
//    获取申请中的课程
    List<Applicant> getApplyingCourses(UUID studentId, String token);
    //获取被拒课程
    List<Applicant> getDeclinedCourses(UUID studentId, String token);

    //教师角色
    //教师用户审批通过某一学生对该门课程的选修申请
    Applicant approve(UUID applicantId, String token);
    //教师用户拒绝某一学生对该门课程的选修申请
    Applicant decline(UUID applicantId, String token);
    //方法获取该门课程所有已获得审批的学生
    List<Applicant> getEnrolledStudents(UUID courseId, String token);
    //方法获取该门课程所正在申请的学生
    List<Applicant> getApplyingStudents(UUID courseId, String token);
    //方法获取该门课程所被拒的学生
    List<Applicant> getDeclinedStudents(UUID courseId, String token);
    //从课程中移除已选修的学生
    Course removeStudent(UUID courseId, UUID studentId, String token);
}
