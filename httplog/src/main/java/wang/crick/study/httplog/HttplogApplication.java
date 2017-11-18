package wang.crick.study.httplog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import wang.crick.study.httplog.aop.HttpLogAspect;

@SpringBootApplication
public class HttplogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttplogApplication.class, args);
	}

	@Bean
	public HttpLogAspect httpLogAspect(){
		return new HttpLogAspect();
	}

}
