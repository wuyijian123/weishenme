package com.example.webdemo.controller;

import com.example.webdemo.handler.BusinessException;
import com.example.webdemo.handler.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public  String index(){
        int a=1/0;
        return "index";
    }
//    @RequestMapping("/error/{i}")
//    public String error(@PathVariable("i") int i) {
//        if (i == 1) {
//            throw new BusinessException("错误捏");
//        } else if (i == 2) {
//            throw new NotFoundException("没有捏");
//        }
//        return "index";
//    }
}
