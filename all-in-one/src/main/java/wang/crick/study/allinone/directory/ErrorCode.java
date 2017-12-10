package wang.crick.study.allinone.directory;

public enum ErrorCode {
    Error(10000, "服务异常"),
    VALID_FAIL(11111, "参数校验失败");

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
