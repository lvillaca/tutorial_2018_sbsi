package datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import dao.ProdutoDAO;
import domain.Filtro;
import domain.Produto;
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

    @Override
    public List<Produto> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Map parametros = dataFetchingEnvironment.getArguments();
        logger.debug("parametros"+parametros);
        try {
            trataParametros(parametros);
            return produtoDAO.getProdutosDeItensDeCompras();
        } finally {
            produtoDAO.resetFiltros();
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
