package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {"boot", "service", "aop"})
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
