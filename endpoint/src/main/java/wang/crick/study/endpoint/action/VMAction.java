package wang.crick.study.endpoint.action;

import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * 查看vm信息
 * <p>
 * Created by crick on 2017/11/25.
 */
@Component
public class VMAction implements EndpointAction {

    private static final VMAction INSTANCE = new VMAction();

    private String version;
    private String startTime;
    private String initHeap;
    private String maxHeap;
    private Set<String> arguments;

    @Override
    public Object execute() {
        INSTANCE.version = System.getProperty("java.runtime.version");
        INSTANCE.startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ManagementFactory.getRuntimeMXBean().getStartTime());
        INSTANCE.initHeap = String.valueOf(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit() / 1024 / 1024).concat("MB");
        INSTANCE.maxHeap = String.valueOf(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax() / 1024 / 1024).concat("MB");
        INSTANCE.arguments = new HashSet<>(ManagementFactory.getRuntimeMXBean().getInputArguments());
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "vm";
    }

    public String getVersion() {
        return version;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getInitHeap() {
        return initHeap;
    }

    public String getMaxHeap() {
        return maxHeap;
    }

    public Set<String> getArguments() {
        return arguments;
    }
}
