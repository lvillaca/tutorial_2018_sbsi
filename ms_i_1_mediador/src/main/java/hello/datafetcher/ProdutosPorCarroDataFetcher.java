package hello.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import hello.dao.ClienteDAO;
import hello.domain.Cliente;
import hello.domain.Filtro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ProdutosPorCarroDataFetcher implements DataFetcher<List<Cliente>> {

    private static final Logger logger = LoggerFactory.getLogger(ProdutosPorCarroDataFetcher.class);

    @Autowired
    ClienteDAO clienteDAO;

    ReentrantLock lock = new ReentrantLock();

    @Override
    public List<Cliente> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Map parametros = dataFetchingEnvironment.getArguments();
        logger.debug("parametros"+parametros);

        lock.lock();
        try {


            trataParametros(parametros);
            int ultimos = 0;

            try { //tenta pegar o campo
                logger.debug("Outros parametros - pesquisar!");
                logger.debug("Estrutura:"+dataFetchingEnvironment.getSelectionSet());
/*
            Field aField = dataFetchingEnvironment.getSelectionSet().get().get("tweets").get(0);
            List<Argument> listArgs = aField.getArguments();
            for (Argument a : listArgs) {
                if (a.getName().equals("ultimos")) {
                    Value aval = a.getValue();
                    logger.debug("argumentos valor" + ((IntValue) aval).getValue());
                    ultimos = ((IntValue) aval).getValue().intValue();
                }
            }
*/
            } catch (Exception e) {
                //sem campo, ignora
            }
/*
        if (ultimos > 0) {
            artistaDAO.addFiltro(Filtro.ultimosTwitters, ultimos);
        }
*/


            logger.debug("parametros" + parametros);
            return clienteDAO.getClientes();



        } finally {
            clienteDAO.resetFiltros();
            lock.unlock();
        }
    }

    private void trataParametros(Map<String, Object> parametros) {
        for (Map.Entry<String, Object> parametro :parametros.entrySet()) {
            try {
                Filtro filtro = Filtro.valueOf(parametro.getKey());
                clienteDAO.addFiltro(filtro,parametro.getValue());
            }
            catch (IllegalArgumentException ia) {
                logger.debug("exc em parametro.getKey"+parametro.getKey());
            }
        }
    }
}
