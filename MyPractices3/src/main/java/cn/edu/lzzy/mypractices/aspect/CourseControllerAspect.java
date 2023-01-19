package cn.edu.lzzy.mypractices.aspect;

import cn.edu.lzzy.mypractices.constant.Messages;
import cn.edu.lzzy.mypractices.entity.User;
import cn.edu.lzzy.mypractices.util.AspectUtils;
import cn.edu.lzzy.mypractices.util.AuthUtils;
import cn.edu.lzzy.mypractices.web.model.ApiResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author lzzy on 2022/11/19.
 * Description:
 */
@Aspect
@Component
public class CourseControllerAspect {
    //切入点1-courseAspect课程查询--.getCourses(..)/ .getPagedCourses(..)
    @Pointcut("execution(* cn.edu.lzzy.mypractices.web.course.CourseApiController.getCourses(..)) || " +
            "execution(* cn.edu.lzzy.mypractices.web.course.CourseApiController.getPagedCourses(..))")
    private void courseAspect() {
    }
    //切入点2-教师课程业务--.getTeachersCourses(..)/ .add(..) update
    @Pointcut("execution(* cn.edu.lzzy.mypractices.web.course.CourseApiController.getTeachersCourses(..)) || " +
            "execution(* cn.edu.lzzy.mypractices.web.course.CourseApiController.add(..)) || "+
  "execution(* cn.edu.lzzy.mypractices.web.course.CourseApiController.update(..))")
    private void teacherAspect() {
    }
    //切入点3-学生课程业务--.getStudentsCourses(..)
    @Pointcut("execution(* cn.edu.lzzy.mypractices.web.course.CourseApiController.getStudentsCourses(..))")
    private void studentAspect() {
    }


    ////切入点1-courseAspect-处理-校验访问者是否学生或者老师
    @Around("courseAspect()")
    public ApiResult aroundCourseMethod(ProceedingJoinPoint point) throws Throwable {
        String token = AspectUtils.getToken(point);
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_TEACHER, User.TYPE_STUDENT})) {
            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }
        return (ApiResult) point.proceed();
    }
    ////切入点2-courseAspect-处理-校验访问者是否老师
    @Around("teacherAspect()")
    public ApiResult aroundTeacherMethod(ProceedingJoinPoint point) throws Throwable {
        String token = AspectUtils.getToken(point);
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_TEACHER})) {
            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }
        return (ApiResult) point.proceed();
    }
    ////切入点3courseAspect-处理-校验访问者是否学生
    @Around("studentAspect()")
    public ApiResult aroundStudentMethod(ProceedingJoinPoint point) throws Throwable {
        String token = AspectUtils.getToken(point);
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_STUDENT})) {
            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }
        return (ApiResult) point.proceed();
    }
}
