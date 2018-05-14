package boot.dao;

import domain.Cliente;
import domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//@Repository
@RepositoryRestResource(collectionResourceRel="cadcliente",path="cadcliente")
public interface ClienteRepository extends CrudRepository<Cliente, String> {
    List<Cliente> findByEnderecoPais(@Param("pais") Country country);
}