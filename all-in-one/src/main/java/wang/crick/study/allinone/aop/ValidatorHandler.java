package wang.crick.study.allinone.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wang.crick.study.allinone.directory.ErrorCode;
import wang.crick.study.allinone.exception.CustomException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class ValidatorHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public Object bindError(BindException e) {
        Map<String, Object> result = new HashMap<>();
        List<FieldError> fieldErrors = e.getFieldErrors();
        Locale locale = LocaleContextHolder.getLocale();
        StringBuilder errorMessage = new StringBuilder();
        fieldErrors.forEach(fieldError -> {
            errorMessage.append(fieldError.getField())
                    .append(":")
                    .append(messageSource.getMessage(fieldError, locale))
                    .append(",");
        });
        result.put("code", ErrorCode.VALID_FAIL.getCode());
        result.put("message", errorMessage);
        return result;
    }
}
