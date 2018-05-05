package boot.dao;

import domain.Country;
import domain.Produto;
import domain.TipoProduto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//(collectionResourceRel = "catalogoprod", path = "catalogoprod")
@Repository
public interface ProdutoRepository extends CrudRepository<Produto, String> {
    List<Produto> findByFornecedorPais(@Param("pais") Country country);
    List<Produto> findByTipo(@Param("tipo") TipoProduto tipo);
}