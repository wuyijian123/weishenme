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


@Component
@Aspect
public class AdminControllerAspect {
    @Pointcut("execution(* cn.edu.lzzy.mypractices.web.admin.*.*(..))")
    private void adminAspect(){}


    @Around("adminAspect()")
    public ApiResult aroundAdminController(ProceedingJoinPoint point) throws Throwable {
        String token = AspectUtils.getToken(point);
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN})){
            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }
        return (ApiResult) point.proceed();
    }
}
