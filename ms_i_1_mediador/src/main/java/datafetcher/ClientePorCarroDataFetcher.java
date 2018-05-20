package datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import dao.ClienteDAO;
import domain.CarroCompras;
import domain.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

@Component
public class ClientePorCarroDataFetcher implements DataFetcher<Cliente> {

    private static final Logger logger = LoggerFactory.getLogger(ClientePorCarroDataFetcher.class);

    @Autowired
    ClienteDAO clienteDAO;

    @Override
    public Cliente get(DataFetchingEnvironment dataFetchingEnvironment) {
        CarroCompras carro = dataFetchingEnvironment.getSource();
        return clienteDAO.getCliente(carro.getCliente().getId());
    }

}
