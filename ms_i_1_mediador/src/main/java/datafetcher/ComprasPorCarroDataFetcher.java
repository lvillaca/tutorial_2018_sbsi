package datafetcher;

import domain.ItemCarroCompras;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import dao.ProdutoDAO;
import domain.CarroCompras;
import domain.Filtro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ComprasPorCarroDataFetcher implements DataFetcher<List<ItemCarroCompras>> {

    private static final Logger logger = LoggerFactory.getLogger(ComprasPorCarroDataFetcher.class);

    @Autowired
    ProdutoDAO produtoDAO;

    ReentrantLock lock = new ReentrantLock();

    @Override
    public List<ItemCarroCompras> get(DataFetchingEnvironment dataFetchingEnvironment) {

        Map parametros = dataFetchingEnvironment.getArguments();
        CarroCompras carro = dataFetchingEnvironment.getSource();

        lock.lock();
        try {

            trataParametros(parametros);
/*
            } catch (Exception e) {
                //sem campo, ignora
            }
*/
            logger.debug("parametros" + parametros);
            return produtoDAO.getProdutosDeItensDeCompras(carro.getListaProdutoEscolhido());

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
