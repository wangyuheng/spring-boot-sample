package wang.crick.study.globalexception.service;

import org.springframework.stereotype.Service;
import wang.crick.study.globalexception.exception.UserException;

/**
 * Created by crick on 2017/11/26.
 */
@Service
public class UserService {

    public String getUsernameById(long id) {
        if (0 == id % 2) {
            throw new IllegalArgumentException("error param!");
        } else {
            throw new UserException("custom exception!");
        }
    }
}
