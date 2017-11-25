package wang.crick.study.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.mvc.ActuatorMediaTypes;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.actuate.endpoint.mvc.HypermediaDisabled;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.crick.study.endpoint.action.DefaultAction;
import wang.crick.study.endpoint.action.EndpointAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toSet;

/**
 * endpoint分发
 * <p>
 * Created by crick on 2017/11/25.
 */
public class CustomEndpointAdapter extends EndpointMvcAdapter {


    private Map<String, EndpointAction> endpointActionMap = new HashMap<>();

    @Autowired
    public void setEndpointActionMap(List<EndpointAction> endpointActionList) {
        endpointActionList.forEach(endpointAction -> endpointActionMap.put(endpointAction.getName(), endpointAction));
    }

    public CustomEndpointAdapter() {
        super(new CustomEndpoint());
    }

    @RequestMapping(value = "/{name:.*}",
            method = RequestMethod.GET, produces = {
            ActuatorMediaTypes.APPLICATION_ACTUATOR_V1_JSON_VALUE,
            MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    @HypermediaDisabled
    public Object dispatcher(@PathVariable String name) {
        if ("help".equalsIgnoreCase(name)) {
            return endpointActionMap.keySet().stream().map(key -> getName() + "/" + key).collect(toSet());
        } else {
            return endpointActionMap.getOrDefault(name, DefaultAction.getInstance()).execute();
        }
    }

}
