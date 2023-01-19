package cn.edu.lzzy.mypractices.repository;
import cn.edu.lzzy.mypractices.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author lzzy on 2021/11/20.
 * Description:
 */
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
    //根据学生用户id获取其所有的申请，并按申请时间降序
    List<Applicant> findApplicantsByStudentIdEqualsOrderByApplyTimeDesc(UUID id);
    //根据学生用户id及申请的状态获取相应状态的申请，并按申请时间降序
    List<Applicant> findApplicantsByStudentIdEqualsAndStatusEqualsOrderByApplyTimeDesc(UUID id, int status);
    //根据课程id获取所有的申请，并按申请时间升序
    List<Applicant> findApplicantsByCourseIdEqualsOrderByApplyTimeAsc(UUID id);
    //根据课程id及申请状态获取相应状态的申请，并按申请时间升序
    List<Applicant> findApplicantsByCourseIdEqualsAndStatusEqualsOrderByApplyTimeAsc(UUID id, int status);
     //根据课程id和学生id获取申请的方法
    Applicant findApplicantByCourseIdAndStudentId(UUID courseId, UUID studentId);
}
