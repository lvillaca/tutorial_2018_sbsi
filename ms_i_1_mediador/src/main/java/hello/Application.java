package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@RestController
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public Greeting home() {
        logger.debug("========================================");
        logger.debug("testando o log!");
        return new Greeting(0,"Hello Docker World 2 Mod");
        // sudo docker system prune
        // sudo docker rmi -f springio/gs-spring-boot-docker
        //sudo ./gradlew build buildDocker
        //sudo docker run -p 8080:8080 springio/gs-spring-boot-docker
        //http://localhost:8080/greeting?name=luis
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
