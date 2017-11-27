package wang.crick.study.allinone.aop;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wang.crick.study.allinone.directory.ErrorCode;
import wang.crick.study.allinone.exception.CustomException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> getErrorObject(int code, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("code", code);
        error.put("message", message);
        return error;
    }

    @ExceptionHandler(Exception.class)
    public Object exceptionHandler() {
        return getErrorObject(ErrorCode.Error.getCode(), ErrorCode.Error.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public Object customException(CustomException e) {
        return getErrorObject(e.getErrorCode(), e.getMessage());
    }

}
