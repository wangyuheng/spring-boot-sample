package wang.crick.study.validator.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.crick.study.validator.domain.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/simpleuser")
public class SimpleUserApi {

    @GetMapping("/{uid}")
    public Object getInfo(@Validated User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 10000);
        result.put("data", user);
        return result;
    }

}
