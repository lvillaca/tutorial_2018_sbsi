/*
package hello.dao;

import hello.domain.Cliente;
import hello.domain.Filtro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CarroCompraDAO {

    private static final Logger logger = LoggerFactory.getLogger(CarroCompraDAO.class);

    //chamadas a microsservicos
    public static final String URL_COM_FILTRO_NACIONALIDADE = "http://ms03_cadcliente:8080/cadcliente/buscapornacionalidade?nacionalidade=";
    public static final String URL_SEM_FILTRO = "http://ms03_cadcliente:8080/cadcliente";

    private Map<Filtro, Object> filtros;

    RestfulServiceProvider<Cliente> clienteRestfulServiceProvider = new RestfulServiceProvider<>();

    public CarroCompraDAO() {
        filtros = new HashMap<>();
    }

    public void addFiltro(Filtro chave, Object valor) {
        filtros.put(chave,valor);
    }

    public List<Cliente> getClientes() {

        List<Cliente> innerList = new ArrayList();

        List<Cliente> outerList = null;

        //Aproveitando a API
        if (filtros.containsKey(Filtro.pais)) {
            String url = URL_COM_FILTRO_NACIONALIDADE+filtros.get(Filtro.pais);
            outerList = clienteRestfulServiceProvider.fetchListForClientes(url);
        } else {
            outerList = clienteRestfulServiceProvider.fetchListForClientes(URL_SEM_FILTRO);
        }

        //Se a API nao dispoe, filtrar depois...
        return outerList.stream().filter(cliente -> {
            if (filtros.containsKey(Filtro.nome)) {
                return cliente.getNome().toLowerCase().contains(((String) filtros.get(Filtro.nome)).toLowerCase());
            } else {
                return true;
            }
        }).collect(Collectors.toList());

    }

    public void resetFiltros() {
        filtros.clear();
    }

}
*/
