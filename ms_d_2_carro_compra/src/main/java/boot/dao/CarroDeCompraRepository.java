package boot.dao;

import domain.CarroDeCompra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroDeCompraRepository  extends CrudRepository<CarroDeCompra, String> {
    List<CarroDeCompra> findByListaProdutoEscolhidoIdProdutoEscolhido(@Param("idProdutoEscolhido")String idProdutoEscolhido);
    List<CarroDeCompra> findByIdCliente(@Param("idCliente")String idCliente);
}