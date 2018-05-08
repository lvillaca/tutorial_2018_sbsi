package hello.dao;

import hello.domain.Filtro;
import hello.domain.ItemCarroCompras;
import hello.domain.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProdutoDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoDAO.class);

    //chamadas a microsservicos
    public static final String URL_COM_FILTRO_NACIONALIDADE = "http://ms01_catalogoprod:8080/catalogoprod/search/findByFornecedorPais?pais=";
    public static final String URL_COM_FILTRO_TIPO = "http://ms01_catalogoprod:8080/catalogoprod/search/findByTipo?tipo=";
    public static final String URL_SEM_FILTRO = "http://ms01_catalogoprod:8080/catalogoprod/";
    public static final String URL_COM_FILTRO_ID = "http://ms01_catalogoprod:8080/catalogoprod/";

    private Map<Filtro, Object> filtros;

    RestfulServiceProvider<Produto> produtoRestfulServiceProvider = new RestfulServiceProvider<>();

    public ProdutoDAO() {
        filtros = new HashMap<>();
    }

    public void addFiltro(Filtro chave, Object valor) {
        filtros.put(chave,valor);
    }

    public List<Produto> getProdutos() {

        List<Produto> outerList = null;

        //Aproveitando a API
        if (filtros.containsKey(Filtro.nacionalidadeFornecedor)) {
            String url = URL_COM_FILTRO_NACIONALIDADE+filtros.get(Filtro.nacionalidadeFornecedor);
            outerList = produtoRestfulServiceProvider.fetchListForProdutos(url);
            filtros.remove(Filtro.nacionalidade); //jah usou
        } else {
            //LIMITACAO DA API
            if (filtros.containsKey(Filtro.tipo)) {
                String url = URL_COM_FILTRO_TIPO+filtros.get(Filtro.tipo);
                outerList = produtoRestfulServiceProvider.fetchListForProdutos(url);
                filtros.remove(Filtro.tipo); //jah usou
            } else {
                outerList = produtoRestfulServiceProvider.fetchListForProdutos(URL_SEM_FILTRO);
            }
        }

        //Se a API nao dispoe de filtros simultaneos, aplicar depois...
        return outerList.stream().filter(produto -> {
            if (filtros.containsKey(Filtro.tipo)) {
                return produto.getTipo().equals(filtros.get(Filtro.tipo));
            } else {
                return true;
            }
        }).collect(Collectors.toList());

    }

    public List<Produto> getProdutos(List<ItemCarroCompras> innerList) {

        if (innerList != null) {
            return
                    //varre lista de itens
                    innerList.stream().map(item -> produtoRestfulServiceProvider.
                            // e popula cada produto
                    fetchListForProduto(URL_COM_FILTRO_ID+item.getProduto().getId())).
            filter(novoProduto -> {
                        //aplica filtros a instancia populada
                if (filtros.containsKey(Filtro.modelo)) {
                    if (!novoProduto.getModelo().equals(filtros.get(Filtro.modelo))) {
                        return false;
                    }
                }
                if (filtros.containsKey(Filtro.tipo)) {
                    if (!novoProduto.getTipo().equals(filtros.get(Filtro.tipo))) {
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void resetFiltros() {
        filtros.clear();
    }

}
