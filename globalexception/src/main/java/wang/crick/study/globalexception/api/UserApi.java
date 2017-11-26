package wang.crick.study.globalexception.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.crick.study.globalexception.service.UserService;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Object getInfo(@PathVariable("id") int id) {
        return userService.getUsernameById(id);
    }

}
