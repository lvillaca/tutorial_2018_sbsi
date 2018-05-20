package service;

import boot.dao.ProdutoRepository;
import domain.Country;
import domain.Fornecedor;
import domain.Produto;
import domain.TipoProduto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Fornecedor lenovo = new Fornecedor("lenovo", Country.PT);

        //Produtos
        Produto produto1 = new Produto("006", "ThinkPad50D", dell, TipoProduto.LAPTOP);
        Produto produto2 = new Produto("007", "XPS13", lenovo, TipoProduto.LAPTOP);

        //persistindo produtos
        logger.debug("persistindo produtos");

        produtoRepository.save(produto1);
        produtoRepository.save(produto2);

        return "Lista de produtos gerada";
    }

}
