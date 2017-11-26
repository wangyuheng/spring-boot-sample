package wang.crick.study.globalexception.exception;

/**
 * Created by crick on 2017/11/26.
 */
public class CustomException extends RuntimeException {

    private int errorCode;

    public CustomException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
