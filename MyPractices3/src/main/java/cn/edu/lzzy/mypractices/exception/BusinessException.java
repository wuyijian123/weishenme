package cn.edu.lzzy.mypractices.exception;

/**
 * @author lzzy_gxy on 2022/11/7.
 * Description:
 */
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
