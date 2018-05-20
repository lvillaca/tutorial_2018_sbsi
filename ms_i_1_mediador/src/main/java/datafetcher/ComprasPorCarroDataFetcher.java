package datafetcher;

import dao.ProdutoDAO;
import domain.CarroCompras;
import domain.Filtro;
import domain.ItemCarroCompras;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ComprasPorCarroDataFetcher implements DataFetcher<List<ItemCarroCompras>> {

    private static final Logger logger = LoggerFactory.getLogger(ComprasPorCarroDataFetcher.class);

    @Autowired
    ProdutoDAO produtoDAO;

    @Override
    public List<ItemCarroCompras> get(DataFetchingEnvironment dataFetchingEnvironment) {

        Map parametros = dataFetchingEnvironment.getArguments();
        logger.debug("parametros" + parametros);

        CarroCompras carro = dataFetchingEnvironment.getSource();

        try {
            trataParametros(parametros);
            return produtoDAO.getProdutosDeItensDeCompras(carro.getListaProdutoEscolhido());

        } finally {
            produtoDAO.resetFiltros();
        }
    }

    private void trataParametros(Map<String, Object> parametros) {
        for (Map.Entry<String, Object> parametro : parametros.entrySet()) {
            try {
                Filtro filtro = Filtro.valueOf(parametro.getKey());
                produtoDAO.addFiltro(filtro, parametro.getValue());
            } catch (IllegalArgumentException ia) {
                logger.debug("erro em parametro:" + parametro.getKey());
            }
        }
    }
}
