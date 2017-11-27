package wang.crick.study.globalexception.exception;

import wang.crick.study.globalexception.directory.ErrorCode;

public class CustomException extends RuntimeException {

    private int errorCode;

    public CustomException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message) {
        super(message);
        this.errorCode = ErrorCode.Error.getCode();
    }

    public int getErrorCode() {
        return errorCode;
    }
}
