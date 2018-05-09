package dao;

import domain.CarroCompras;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarroComprasDAO {

    private static final Logger logger = LoggerFactory.getLogger(CarroComprasDAO.class);

    //chamadas a microsservico
    public static final String URL_SEM_FILTRO = "http://ms02_carrocompra:8080/carroscompra";

    RestfulServiceProvider clienteRestfulServiceProvider = new RestfulServiceProvider();

    public List<CarroCompras> getCarroCompras() {
        List<CarroCompras> list;
        list = clienteRestfulServiceProvider.fetchListForCarroCompras(URL_SEM_FILTRO);
        System.out.println("\n\nlistas"+list);
        return list;
    }
}