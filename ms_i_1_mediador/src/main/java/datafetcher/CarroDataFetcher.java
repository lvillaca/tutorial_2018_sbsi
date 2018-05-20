package datafetcher;

import dao.CarroComprasDAO;
import domain.CarroCompras;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarroDataFetcher implements DataFetcher<List<CarroCompras>> {

    private static final Logger logger = LoggerFactory.getLogger(CarroDataFetcher.class);

    @Autowired
    CarroComprasDAO carroCompraDAO;

    @Override
    public List<CarroCompras> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return carroCompraDAO.getCarroCompras();
    }

}
