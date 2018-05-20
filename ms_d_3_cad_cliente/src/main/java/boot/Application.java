package boot;

import domain.StructCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {"boot","service","aop"})
@EntityScan(basePackages = "domain")
@RestController
/**
 * @author Luis
 * Classe principal Spring Boot.
 */
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
