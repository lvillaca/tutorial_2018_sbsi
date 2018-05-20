package dao;

import domain.Cliente;
import domain.Filtro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ClienteDAO {

    private static final Logger logger = LoggerFactory.getLogger(ClienteDAO.class);

    //chamadas a microsservicos
    public static final String URL_COM_FILTRO_PAIS = "http://ms03_cadcliente:8080/cadcliente/search/findByEnderecoPais?pais=";
    public static final String URL_COM_FILTRO_ID = "http://ms03_cadcliente:8080/cadcliente/";
    public static final String URL_SEM_FILTRO = "http://ms03_cadcliente:8080/cadcliente";

    private Map<Filtro, Object> filtros;

    RestfulServiceProvider clienteRestfulServiceProvider = new RestfulServiceProvider();

    public ClienteDAO() {
        filtros = new HashMap<>();
    }

    public void addFiltro(Filtro chave, Object valor) {
        filtros.put(chave,valor);
    }

    public Cliente getCliente(String id) {
        String url = URL_COM_FILTRO_ID+id;
        return  clienteRestfulServiceProvider.fetchListForCliente(url);
    }

    public List<Cliente> getClientes() {

        List<Cliente> outerList = null;

        //Aproveitando a API
        if (filtros.containsKey(Filtro.pais)) {
            String url = URL_COM_FILTRO_PAIS +filtros.get(Filtro.pais);
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
