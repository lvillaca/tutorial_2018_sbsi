package datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import dao.ClienteDAO;
import domain.Cliente;
import domain.Filtro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ClienteDataFetcher implements DataFetcher<List<Cliente>> {

    private static final Logger logger = LoggerFactory.getLogger(ClienteDataFetcher.class);

    @Autowired
    ClienteDAO clienteDAO;

    @Override
    public List<Cliente> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Map parametros = dataFetchingEnvironment.getArguments();
        logger.debug("parametros"+parametros);

        try {
            trataParametros(parametros);
            return clienteDAO.getClientes();
        } finally {
            clienteDAO.resetFiltros();
        }
    }

    private void trataParametros(Map<String, Object> parametros) {
        for (Map.Entry<String, Object> parametro :parametros.entrySet()) {
            try {
                Filtro filtro = Filtro.valueOf(parametro.getKey());
                clienteDAO.addFiltro(filtro,parametro.getValue());
            }
            catch (IllegalArgumentException ia) {
                logger.debug("erro de parametro:"+parametro.getKey());
            }
        }
    }
}
