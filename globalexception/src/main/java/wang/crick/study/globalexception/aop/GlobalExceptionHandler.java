package wang.crick.study.globalexception.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wang.crick.study.globalexception.directory.ErrorCode;
import wang.crick.study.globalexception.exception.CustomException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crick on 2017/11/26.
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
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
