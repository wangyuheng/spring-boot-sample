package wang.crick.study.globalexception.directory;

/**
 * Created by crick on 2017/11/26.
 */
public enum ErrorCode {
    Error(10000, "服务异常"),
    UserIdError(10001, "用户id异常");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
