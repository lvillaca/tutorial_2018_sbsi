package service;

import boot.dao.ProdutoRepository;
import domain.Country;
import domain.Fornecedor;
import domain.Produto;
import domain.TipoProduto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private ProdutoRepository produtoRepository;

    @RequestMapping("/save")
    public String save() {

        //Fornecedores
        Fornecedor dell = new Fornecedor("dell", Country.CA);
        Fornecedor microsoft = new Fornecedor("microsoft", Country.US);
        Fornecedor lenovo = new Fornecedor("lenovo", Country.PT);

        //Produtos
        Produto produto1 = new Produto("006","ThinkPad50D",dell,TipoProduto.LAPTOP);
        Produto produto2 = new Produto("007","XPS13",lenovo,TipoProduto.LAPTOP);

        //fechando um produto
        produtoRepository.save(produto1);
        produtoRepository.save(produto2);

        return "Cenario de produtos gerado";
    }

    /*
    @GetMapping("/catalogoprod/")
    public List getAllProds() {
        //iterable to list
        return StreamSupport.stream(produtoRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }


    @GetMapping("/catalogoprod/{idProduto}")
    public Produto getByIdProduto(@PathVariable String idProduto){
        return produtoRepository.findOne(idProduto);
    }

    @RequestMapping(value="/catalogoprod/buscaportipo", method = RequestMethod.GET, produces = "application/json")
    public List getByTipo(@RequestParam(value="tipo", defaultValue="") String tipo) {
        return produtoRepository.findByTipo(TipoProduto.valueOf(tipo));
    }

    @RequestMapping(value="/catalogoprod/buscapaisfornecedor", method = RequestMethod.GET, produces = "application/json")
    public List getByCountry(@RequestParam(value="pais", defaultValue="") String pais) {
        return produtoRepository.findByFornecedorPais(Country.valueOf(pais));
    }

    */

}
