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
 * @author lzzy on 2022/11/17.
 * Description:
 */
////配置该类为组件类，Spring Boot在组件扫描时会扫描所有组件类
@Component
//标识该类为切面
@Aspect
public class AdminControllerAspect {
    /**@Pointcut定义切入点
    * 若不在此处定义，则需要直接在每个相关方法的Advice注解上定义切入点
    * 此处定义后，其他地方使用同样的切入点就不需要再写execution，直接写切入点的方法名即可
    **/

    /**
     * execution 表达式语法
     * 第一个*号：表示返回类型，*号表示所有的类型
     * 包名：表示需要拦截的包名，上面表示admin包内所有的类的所有方法
     * *(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个点表示任何参数
     * 还可在后面添加参数约束，如&& args(token) ，意即拦截的方法要有1个token参数（可配置多个参数）
     **/
    @Pointcut("execution(* cn.edu.lzzy.mypractices.web.admin.*.*(..))")
    private void adminAspect(){}

    /**
     *  使用Pointcut定义拦截范围，但也可直接对每个方法使用下面的注解
     *  @Around("execution(* cn.edu.lzzy.mypractices.web.admin.*.*(..))")
     */
    @Around("adminAspect()")
    public ApiResult aroundAdminController(ProceedingJoinPoint point) throws Throwable {
       //从连接点上获取token
        String token = AspectUtils.getToken(point);
        //利用tokend检验用户是否合法，我们这里是校验类型是否为admin
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN})){
//            如果是非法用户，返回封装错我消息

            return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
        }
        //如果合法用户，把请求数据封装为ApiResult格式返回
        return (ApiResult) point.proceed();
    }
}
