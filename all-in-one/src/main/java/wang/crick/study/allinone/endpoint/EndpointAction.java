package wang.crick.study.allinone.endpoint;

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
