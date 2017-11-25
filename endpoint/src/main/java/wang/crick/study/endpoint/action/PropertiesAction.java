package wang.crick.study.endpoint.action;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 获取配置文件
 * <p>
 * Created by crick on 2017/11/25.
 */
@Component
public class PropertiesAction implements EndpointAction {

    @Override
    public Object execute() {
        try {
            return PropertiesLoaderUtils.loadAllProperties("application.properties");
        } catch (IOException e) {
            return "read application fail! error: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "properties";
    }
}
