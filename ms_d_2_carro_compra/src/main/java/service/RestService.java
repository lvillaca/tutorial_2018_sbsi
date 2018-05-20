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
import java.util.Calendar;
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
        salvarLote(0,1);
        salvarLote(1,1);
        salvarLote(2,2);
        salvarLote(3,1);
        salvarLote(4,1);
        salvarLote(5,1);
        salvarLote(6,1);
        salvarLote(7,1);
        salvarLote(8,1);
        salvarLote(9,1);
        salvarLote(10,1);
        salvarLote(11,4);
        salvarLote(12,4);
        salvarLote(13,5);

        return "Carro de compras salvo";
    }

    private void salvarLote(int numMesesAntes, int quantidade) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0 - numMesesAntes);
        Date dt = calendar.getTime();

        // itens de compra
        List<ItemCarroDeCompra> items = new ArrayList<>();
        items.add(new ItemCarroDeCompra("007",quantidade*1,24));
        items.add(new ItemCarroDeCompra("006",quantidade*2,23));
        //fechando um carro
        CarroDeCompra carr = new CarroDeCompra("0704354"+numMesesAntes,items,"006", dt);
        carroDeCompraRepository.save(carr);

        // itens de compra
        List<ItemCarroDeCompra> items2 = new ArrayList<>();
        items2.add(new ItemCarroDeCompra("006",quantidade+2,24));
        items2.add(new ItemCarroDeCompra("007",quantidade+1,44));
        //fechando outro carro
        CarroDeCompra carr2 = new CarroDeCompra("070424"+numMesesAntes,items2,"007", dt);
        carroDeCompraRepository.save(carr2);
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
