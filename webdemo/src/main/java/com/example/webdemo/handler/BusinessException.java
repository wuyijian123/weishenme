package com.example.webdemo.handler;

/**
 * @author lzzy_gxy on 2021/11/7.
 * Description:
 */
public class BusinessException extends RuntimeException {
    private String errInfo;

    public BusinessException(String message) {
        super(message);
        errInfo = message;
    }

    public String getErrInfo() {
        System.out.println("ces");
        return errInfo;
    }
}
