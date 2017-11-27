package wang.crick.study.allinone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import wang.crick.study.allinone.endpoint.CustomEndpoint;
import wang.crick.study.allinone.endpoint.CustomEndpointAdapter;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(CustomEndpoint.class)
    public CustomEndpointAdapter customEndpointAdapter() {
        return new CustomEndpointAdapter();
    }

}
