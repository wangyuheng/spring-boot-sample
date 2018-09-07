package wang.crick.study.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class I18nApplication {

    public static void main(String[] args) {
        SpringApplication.run(I18nApplication.class, args);
    }

    @Autowired
    private MessageSource messageSource;

    @GetMapping("hello")
    public Object hello(HttpServletRequest request) {
        return messageSource.getMessage("10000", new Object[]{}, LocaleContextHolder.getLocale());
    }

}
