package service;

import boot.dao.ClienteRepository;
import domain.Country;
import domain.Endereco;
import domain.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@ComponentScan(basePackages = {"boot","service"})
@RestController
/**
 * @author Luis
 * Classe principal Spring Boot.
 */
public class RestService {
    private static final Logger logger = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @RequestMapping("/save")
    public String save() {

        //Endereco
        Endereco addr1 = new Endereco();
        addr1.setNome("Montana Av, SE");
        addr1.setPais(Country.CA);
        Endereco addr3 = new Endereco();
        addr3.setNome("Hollywood Blvd, CA");
        addr3.setPais(Country.US);

        //Clientes
        Cliente cliente1 = new Cliente("006","Jason Becker",addr1);
        Cliente cliente2 = new Cliente("007","Vinnie Moore",addr3);

        //fechando um produto
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        return "Instâncias de clientes atualizadas";
    }

}


