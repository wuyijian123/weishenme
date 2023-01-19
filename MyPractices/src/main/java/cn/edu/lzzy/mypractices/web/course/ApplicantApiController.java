package cn.edu.lzzy.mypractices.web.course;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.edu.lzzy.mypractices.constant.*;
import cn.edu.lzzy.mypractices.entity.*;
import cn.edu.lzzy.mypractices.service.ApplicantService;
import cn.edu.lzzy.mypractices.util.StringUtils;
import cn.edu.lzzy.mypractices.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author lzzy on 2022/11/22.
 * Description:
 */
@Api(tags = "课程注册相关业务")
@CrossOrigin
@RestController
@RequestMapping(ApiConstant.ROUTE_APPLY_ROOT)
public class ApplicantApiController {
    private final ApplicantService service;

    @Autowired
    public ApplicantApiController(ApplicantService service){
        this.service = service;
    }

    @ApiOperation("学生申请注册课程")
    @PostMapping(value = ApiConstant.ROUTE_APPLY_STUDENT_APPLY, produces = ApiConstant.API_PRODUCES)
    public ApiResult apply(@RequestBody JSONObject json){
        String token = json.getString(ApiConstant.KEY_TOKEN);
        String strStudentId = json.getString(ApiConstant.KEY_APPLY_STUDENT_ID);
        String strCourseId = json.getString(ApiConstant.KEY_APPLY_COURSE_ID);
        if (StringUtils.isEmpty(token) ||
                StringUtils.isEmpty(strStudentId) ||
                StringUtils.isEmpty(strCourseId)){
            return new ApiResult(false, Messages.INCOMPLETE_INFO.toString(), null);
        }
        try {
            UUID studentId = UUID.fromString(strStudentId);
            UUID courseId = UUID.fromString(strCourseId);
            Applicant applicant = service.apply(studentId, courseId, token);
            if (applicant == null){
                return new ApiResult(false, Messages.WRONG_ID.toString(), null);
            }
            return new ApiResult(true, "已申请注册课程", new VmApplicant(applicant));
        } catch (Exception e){
            return new ApiResult(false, Messages.WRONG_ID.toString(), e.getMessage());
        }
    }

    private ApiResult transform(List<Applicant> applicants) {
        if (applicants == null){
            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }
        List<VmApplicant> vmApplicants = new ArrayList<>();
        applicants.forEach(applicant -> vmApplicants.add(new VmApplicant(applicant)));
        return new ApiResult(true, applicants.size() + "", vmApplicants);
    }

    @ApiOperation("查询某学生所有申请信息")
    @GetMapping(ApiConstant.ROUTE_APPLY_STUDENT_COURSES)
    public ApiResult getStudentsApplicants(@RequestParam(name = "id") UUID studentId, @RequestParam String token){
        List<Applicant> applicants = service.getAllCourses(studentId, token);
        return transform(applicants);
    }

    @ApiOperation("查询某学生已注册的课程")
    @GetMapping(ApiConstant.ROUTE_APPLY_STUDENT_ENROLLED)
    public ApiResult getEnrolledApplicants(@RequestParam(name = "id") UUID studentId, @RequestParam String token){
        List<Applicant> applicants = service.getEnrolledCourses(studentId, token);
        return transform(applicants);
    }

    @ApiOperation("查询某学生申请中的课程")
    @GetMapping(ApiConstant.ROUTE_APPLY_STUDENT_APPLYING)
    public ApiResult getApplyingApplicants(@RequestParam(name = "id") UUID studentId, @RequestParam String token){
        List<Applicant> applicants = service.getApplyingCourses(studentId, token);
        return transform(applicants);
    }

    @ApiOperation("查询某学生被拒绝的课程注册信息")
    @GetMapping(ApiConstant.ROUTE_APPLY_STUDENT_DECLINED)
    public ApiResult getDeclinedApplicants(@RequestParam(name = "id") UUID studentId, @RequestParam String token){
        List<Applicant> applicants = service.getDeclinedCourses(studentId, token);
        return transform(applicants);
    }

    @ApiOperation("审批学生申请")
    @PostMapping(value = ApiConstant.ROUTE_APPLY_APPROVE, produces = ApiConstant.API_PRODUCES)
    public ApiResult approve(@RequestBody JSONObject json){
        String token = json.getString(ApiConstant.KEY_TOKEN);
        String strApplicantId = json.getString(ApiConstant.KEY_APPLY_APPLICANT_ID);
        if (StringUtils.isEmpty(token) ||
                StringUtils.isEmpty(strApplicantId)){
            return new ApiResult(false, Messages.INCOMPLETE_INFO.toString(), null);
        }
        try {
            UUID applicantId = UUID.fromString(strApplicantId);
            Applicant applicant = service.approve(applicantId, token);
            if (applicant == null){
                return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
            }
            return new ApiResult(true, "已完成审批学生", new VmApplicant(applicant));
        } catch (IllegalArgumentException e){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
    }

    @ApiOperation("拒绝学生申请")
    @PostMapping(value = ApiConstant.ROUTE_APPLY_DECLINE, produces = ApiConstant.API_PRODUCES)
    public ApiResult decline(@RequestBody JSONObject json){
        String token = json.getString(ApiConstant.KEY_TOKEN);
        String strApplicantId = json.getString(ApiConstant.KEY_APPLY_APPLICANT_ID);
        if (StringUtils.isEmpty(token) ||
                StringUtils.isEmpty(strApplicantId)){
            return new ApiResult(false, Messages.INCOMPLETE_INFO.toString(), null);
        }
        try {
            UUID applicantId = UUID.fromString(strApplicantId);
            Applicant applicant = service.decline(applicantId, token);
            if (applicant == null){
                return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
            }
            return new ApiResult(true, "已完成驳回申请", new VmApplicant(applicant));
        } catch (IllegalArgumentException e){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
    }

    @ApiOperation("教师查询申请其某课程的所有申请信息")
    @GetMapping(ApiConstant.ROUTE_APPLY_TEACHER_APPLYING)
    public ApiResult getApplyingStudents(@RequestParam(name = "id") UUID courseId, @RequestParam String token){
        List<Applicant> applicants = service.getApplyingStudents(courseId, token);
        return transform(applicants);
    }

    @ApiOperation("教师查询已注册其某课程的所有信息")
    @GetMapping(ApiConstant.ROUTE_APPLY_TEACHER_ENROLLED)
    public ApiResult getEnrolledStudents(@RequestParam(name = "id") UUID courseId, @RequestParam String token){
        List<Applicant> applicants = service.getEnrolledStudents(courseId, token);
        return transform(applicants);
    }

    @ApiOperation("教师查询其某课程已拒绝的所有申请信息")
    @GetMapping(ApiConstant.ROUTE_APPLY_TEACHER_DECLINED)
    public ApiResult getDeclinedStudents(@RequestParam(name = "id") UUID courseId, @RequestParam String token){
        List<Applicant> applicants = service.getDeclinedStudents(courseId, token);
        return transform(applicants);
    }

    @ApiOperation("教师将某学生从其课程中移除")
    @PostMapping(value = ApiConstant.ROUTE_APPLY_REMOVE, produces = ApiConstant.API_PRODUCES)
    public ApiResult remove(@RequestBody JSONObject json){
        String token = json.getString(ApiConstant.KEY_TOKEN);
        String strCourseId = json.getString(ApiConstant.KEY_APPLY_COURSE_ID);
        String strStudentId = json.getString(ApiConstant.KEY_APPLY_STUDENT_ID);
        if (StringUtils.isEmpty(token) ||
                StringUtils.isEmpty(strCourseId) ||
                StringUtils.isEmpty(strStudentId)){
            return new ApiResult(false, Messages.INCOMPLETE_INFO.toString(), null);
        }
        try {
            UUID courseId = UUID.fromString(strCourseId);
            UUID studentId = UUID.fromString(strStudentId);
            Course course = service.removeStudent(courseId, studentId, token);
            if (course == null){
                return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
            }
            return new ApiResult(true, "已完成移除学生", VmCourse.create(course, true));
        }catch (IllegalArgumentException e){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
    }
}
