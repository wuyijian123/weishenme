package com.example.webdemo.handler;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//import javax.servlet.http.HttpServletRequest;

/**
 * @author lzzy_gxy on 2021/11/7.
 * Description:
 */
@ControllerAdvice
public class PracticeExceptionHandler  {
    private  final  static String  ERROR_PAGE="error/error";

    //    @ExceptionHandler(BusinessException.class)
//    public ModelAndView businessExceptionHandler(HttpServletRequest request, BusinessException e){
//        String errInfo = "业务异常：" + e.getErrInfo();
//        ModelAndView mv = new ModelAndView(ERROR_PAGE);
//        mv.addObject("title", errInfo);
//        mv.addObject("url", request.getRequestURL().toString());
//        mv.addObject("exception", e);
//        mv.addObject("emsg",e.getMessage());
//        mv.addObject("est",e.getStackTrace());
//        return mv;
//    }
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView notFoundExceptionHandler(HttpServletRequest request,NotFoundException e){
//        String errInfo = "无法找到页面:" + e.getErrInfo();
////        System.out.println(errInfo.toString());
//        ModelAndView mv = new ModelAndView(ERROR_PAGE);
//        mv.addObject("title", errInfo);
//        mv.addObject("url", request.getRequestURL().toString());
//        mv.addObject("exception", e);
//        mv.addObject("emsg",e.getMessage());
//        mv.addObject("est",e.getStackTrace());
//        return mv;
//    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
            //有返回状态注解的异常，不在这里处理
        }
        ModelAndView mv = new ModelAndView(ERROR_PAGE);
        mv.addObject("title", e.getMessage());
        mv.addObject("url", request.getRequestURL().toString());
        mv.addObject("exception", e);
        mv.addObject("emsg",e.getMessage());
        mv.addObject("est",e.getStackTrace());
//        mv.setViewName();
        return mv;
    }
}
