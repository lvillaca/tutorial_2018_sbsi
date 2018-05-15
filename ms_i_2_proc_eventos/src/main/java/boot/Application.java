package boot;

import domain.StructCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {"boot","service"})
@RestController
/**
 * @author Luis
 * Classe principal Spring Boot.
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    int id = 0;

    /**
     * Servico na raiz, apenas teste de conectividade sem banco
     * @return json com propriedades
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public StructCheck home() {
        logger.debug("Chamada ao componente - teste de conectividade");
        return new StructCheck(id++,"OK Docker SpringBoot Gradle");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
