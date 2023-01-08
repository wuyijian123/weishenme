package cn.edu.lzzy.mypractices.exception;


public class BusinessException extends RuntimeException {
    private String errInfo;

    public BusinessException(String message){
        super(message);
        errInfo = message;
    }

    public String getErrInfo() {
        return errInfo;
    }
}
