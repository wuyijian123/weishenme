package com.example.webdemo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzzy_gxy on 2021/11/7.
 * Description:
 */
//@RestController
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public String errInfo;
    public NotFoundException(String message){
        super(message);
    }

    public String getErrInfo() {
        System.out.println("没有找到");
        return errInfo;
    }
}
