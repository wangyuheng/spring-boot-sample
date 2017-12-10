package wang.crick.study.validator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.crick.study.validator.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{uid}")
    public Object getInfo(@Validated User user, BindingResult bindingResult) {
        Map<String, Object> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            Locale locale = LocaleContextHolder.getLocale();
            StringBuilder errorMessage = new StringBuilder();
            fieldErrors.forEach(fieldError -> {
                errorMessage.append(fieldError.getField())
                        .append(":")
                        .append(messageSource.getMessage(fieldError, locale))
                        .append(",");
            });
            result.put("code", 10001);
            result.put("message", errorMessage);
        } else {
            result.put("code", 10000);
            result.put("data", user);
        }
        return result;
    }

}
