package hello.dao;

import hello.domain.CarroCompras;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarroComprasDAO {

    private static final Logger logger = LoggerFactory.getLogger(CarroComprasDAO.class);

    //chamadas a microsservicos
//    public static final String URL_COM_FILTRO_CLIENTE = "http://ms02_carrocompra:8080/carroscompra/buscaporcliente?id=";
//    public static final String URL_COM_FILTRO_PRODUTO = "http://ms02_carrocompra:8080/carroscompra/buscaporproduto?id=";
    public static final String URL_SEM_FILTRO = "http://ms02_carrocompra:8080/carroscompra";

//    private Map<Filtro, Object> filtros;

    RestfulServiceProvider<CarroCompras> clienteRestfulServiceProvider = new RestfulServiceProvider<>();
/*

    public CarroComprasDAO() {
        filtros = new HashMap<>();
    }

    public void addFiltro(Filtro chave, Object valor) {
        filtros.put(chave,valor);
    }
*/

    public List<CarroCompras> getCarroCompras() {
        List<CarroCompras> list;
        list = clienteRestfulServiceProvider.fetchListForCarroCompras(URL_SEM_FILTRO);
        System.out.println("\n\nlistas"+list);
        return list;
    }
/*
    public void resetFiltros() {
        filtros.clear();
    }*/

}