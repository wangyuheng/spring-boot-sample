package wang.crick.study.allinone.exception;


import wang.crick.study.allinone.directory.ErrorCode;

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
