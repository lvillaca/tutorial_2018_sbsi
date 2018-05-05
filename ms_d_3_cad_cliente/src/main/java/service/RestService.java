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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        addr1.setNacionalidade(Country.CA);
        Endereco addr3 = new Endereco();
        addr3.setNome("Hollywood Blvd, CA");
        addr3.setNacionalidade(Country.US);

        //Clientes
        Cliente cliente1 = new Cliente("006","Jason Becker",addr1);
        Cliente cliente2 = new Cliente("007","Vinnie Moore",addr3);

        //fechando um produto
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        return "Cenario de produtos gerado";
    }

    @GetMapping("/cadcliente")
    public List getAllCarrosDeCompra() {
        //iterable to list
        return StreamSupport.stream(clienteRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }


    @GetMapping("/cadcliente/{idCliente}")
    public Cliente getByIdCliente(@PathVariable String idCliente){
        return clienteRepository.findOne(idCliente);
    }

    @RequestMapping(value="/cadcliente/buscapornacionalidade", method = RequestMethod.GET, produces = "application/json")
    public List getByNacionalidade(@RequestParam(value="nacionalidade", defaultValue="") String nacionalidade) {
        return clienteRepository.findByEnderecoNacionalidade(Country.valueOf(nacionalidade));
    }

}


