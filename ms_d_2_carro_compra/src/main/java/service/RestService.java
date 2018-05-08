package service;

import boot.dao.CarroDeCompraRepository;
import domain.CarroDeCompra;
import domain.ItemCarroDeCompra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ComponentScan(basePackages = {"boot","service"})
@RestController
/**
 * @author Luis
 * Classe principal De Servico.
 */
public class RestService {
    private static final Logger logger = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private CarroDeCompraRepository carroDeCompraRepository;

    @RequestMapping("/save")
    public String save() {

        // itens de compra
        List<ItemCarroDeCompra> items = new ArrayList<>();
        items.add(new ItemCarroDeCompra("007",33,234));
        items.add(new ItemCarroDeCompra("006",343,23454));
        //fechando um carro
        CarroDeCompra carr = new CarroDeCompra("0704354",items,"006", new Date());
        carroDeCompraRepository.save(carr);

        // itens de compra
        List<ItemCarroDeCompra> items2 = new ArrayList<>();
        items2.add(new ItemCarroDeCompra("006",33,234));
        items2.add(new ItemCarroDeCompra("007",343,2454));
        //fechando outro carro
        CarroDeCompra carr2 = new CarroDeCompra("070424",items2,"007", new Date());
        carroDeCompraRepository.save(carr2);

        return "Carro de compras salvo";
    }

    @GetMapping("/carroscompra")
    public Iterable<CarroDeCompra> getAllCarrosDeCompra() {
        //iterable to list
        //return StreamSupport.stream(carroDeCompraRepository.findAll().spliterator(),false).collect(Collectors.toList());
        return carroDeCompraRepository.findAll();
    }


    @GetMapping("/carroscompra/{idSessao}")
    public CarroDeCompra getByIdSessao(@PathVariable String idSessao){
        return carroDeCompraRepository.findOne(idSessao);
    }

    @RequestMapping(value="/carroscompra/buscaporproduto", method = RequestMethod.GET, produces = "application/json")
    public List getByIdProduto(@RequestParam(value="id", defaultValue="") String idProduto) {
        return carroDeCompraRepository.findByListaProdutoEscolhidoIdProdutoEscolhido(idProduto);
    }

    @RequestMapping(value="/carroscompra/buscaporcliente", method = RequestMethod.GET, produces = "application/json")
    public List getByIdCliente(@RequestParam(value="id", defaultValue="") String idCliente) {
        return carroDeCompraRepository.findByIdCliente(idCliente);
    }

}
