package hello.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import hello.dao.ProdutoDAO;
import hello.domain.Cliente;
import hello.domain.Filtro;
import hello.domain.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ProdutoDataFetcher implements DataFetcher<List<Produto>> {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoDataFetcher.class);

    @Autowired
    ProdutoDAO produtoDAO;

    ReentrantLock lock = new ReentrantLock();

    @Override
    public List<Produto> get(DataFetchingEnvironment dataFetchingEnvironment) {
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
            return produtoDAO.getProdutos();



        } finally {
            produtoDAO.resetFiltros();
            lock.unlock();
        }
    }

    private void trataParametros(Map<String, Object> parametros) {
        for (Map.Entry<String, Object> parametro :parametros.entrySet()) {
            try {
                Filtro filtro = Filtro.valueOf(parametro.getKey());
                produtoDAO.addFiltro(filtro,parametro.getValue());
            }
            catch (IllegalArgumentException ia) {
                logger.debug("exc em parametro.getKey"+parametro.getKey());
            }
        }
    }
}
