package dao;

import domain.Filtro;
import domain.ItemCarroCompras;
import domain.Produto;
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
    public static final String URL_COM_FILTRO_NACIONALIDADE_E_TIPO = "http://ms01_catalogoprod:8080/catalogoprod/search/findByFornecedorPaisAndTipo?pais=PAIS&tipo=TIPO";

    public static final String URL_SEM_FILTRO = "http://ms01_catalogoprod:8080/catalogoprod";
    public static final String URL_COM_FILTRO_ID = "http://ms01_catalogoprod:8080/catalogoprod/";

    private Map<Filtro, Object> filtros;

    RestfulServiceProvider produtoRestfulServiceProvider = new RestfulServiceProvider();

    public ProdutoDAO() {
        filtros = new HashMap<>();
    }

    public void addFiltro(Filtro chave, Object valor) {
        filtros.put(chave,valor);
    }

    public List<Produto> getProdutosDeItensDeCompras() {

        List<Produto> outerList = null;

        //Aproveitando a API
        if (filtros.containsKey(Filtro.paisFornecedor)) {
            if (filtros.containsKey(Filtro.tipo)) {
                String url = URL_COM_FILTRO_NACIONALIDADE_E_TIPO
                        .replaceFirst("PAIS",(String)filtros.get(Filtro.paisFornecedor))
                        .replaceFirst("TIPO",(String)filtros.get(Filtro.tipo));
                System.out.println(url);
                outerList = produtoRestfulServiceProvider.fetchListForProdutos(url);
            } else {
                String url = URL_COM_FILTRO_NACIONALIDADE+filtros.get(Filtro.paisFornecedor);
                System.out.println(url);
                outerList = produtoRestfulServiceProvider.fetchListForProdutos(url);
            }

        } else {
            if (filtros.containsKey(Filtro.tipo)) {
                String url = URL_COM_FILTRO_TIPO+filtros.get(Filtro.tipo);
                System.out.println(url);
                outerList = produtoRestfulServiceProvider.fetchListForProdutos(url);
            } else {
                System.out.println(URL_SEM_FILTRO);
                outerList = produtoRestfulServiceProvider.fetchListForProdutos(URL_SEM_FILTRO);
            }
        }

        return outerList;

    }

    public List<ItemCarroCompras> getProdutosDeItensDeCompras(List<ItemCarroCompras> innerList) {

        if (innerList != null) {

            //varre lista de itens e popula cada produto
            innerList.stream().forEach(item ->
                    item.refreshProduto(produtoRestfulServiceProvider.
                        fetchListForProduto(URL_COM_FILTRO_ID+item.getProduto().getId())));

            //retorna o que for valido
            return innerList.stream().filter(novoItemProduto -> {
                        //aplica filtros a instancia populada
                if (filtros.containsKey(Filtro.modelo)) {
                    if (!novoItemProduto.getProduto().getModelo().equals(filtros.get(Filtro.modelo))) {
                        return false;
                    }
                }
                if (filtros.containsKey(Filtro.tipo)) {
                    if (!novoItemProduto.getProduto().getTipo().equals(filtros.get(Filtro.tipo))) {
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
