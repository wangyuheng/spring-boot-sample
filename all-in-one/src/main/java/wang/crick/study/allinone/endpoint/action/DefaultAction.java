package wang.crick.study.allinone.endpoint.action;

import org.springframework.stereotype.Component;
import wang.crick.study.allinone.endpoint.EndpointAction;

/**
 * 默认endpoint
 * <p>
 * Created by crick on 2017/11/25.
 */
@Component
public class DefaultAction implements EndpointAction {


    private static final DefaultAction INSTANCE = new DefaultAction();

    public static DefaultAction getInstance() {
        return INSTANCE;
    }

    @Override
    public Object execute() {
        return "try /help for action list";
    }

    @Override
    public String getName() {
        return "default";
    }

}
