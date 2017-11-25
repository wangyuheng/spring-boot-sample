package wang.crick.study.endpoint;

import org.springframework.boot.actuate.endpoint.Endpoint;
import wang.crick.study.endpoint.action.DefaultAction;

/**
 * 入口endpoint
 * <p>
 * Created by crick on 2017/11/25.
 */
public class CustomEndpoint implements Endpoint {

    @Override
    public String getId() {
        return "custom";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public Object invoke() {
        return DefaultAction.getInstance().execute();
    }
}
