package wang.crick.study.endpoint.action;

import java.io.Serializable;

/**
 * endpoint事件
 * <p>
 * Created by crick on 2017/11/25.
 */
public interface EndpointAction extends Serializable {
    Object execute();

    String getName();
}
