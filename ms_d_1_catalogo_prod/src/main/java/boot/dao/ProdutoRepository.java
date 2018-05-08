package boot.dao;

import domain.Country;
import domain.Produto;
import domain.TipoProduto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
@RepositoryRestResource(collectionResourceRel = "catalogoprod", path = "catalogoprod")
public interface ProdutoRepository extends CrudRepository<Produto, String> {
    List<Produto> findByFornecedorPais(@Param("pais") Country country);
    List<Produto> findByTipo(@Param("tipo") TipoProduto tipo);
}