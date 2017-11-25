package wang.crick.study.endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EndpointApplication {

    public static void main(String[] args) {
        SpringApplication.run(EndpointApplication.class, args);
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(CustomEndpoint.class)
    public CustomEndpointAdapter customEndpointAdapter() {
        return new CustomEndpointAdapter();
    }
}
