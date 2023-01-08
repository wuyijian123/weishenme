package cn.edu.lzzy.mypractices.web.model;

/**
 * @author lzzy on 2022/11/10.
 * Description:封装控制器返回的消息
 */
public class ApiResult {
    private final boolean success;
    private final String message;
    private final Object data;

    public ApiResult(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
