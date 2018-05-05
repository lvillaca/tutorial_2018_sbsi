package boot.dao;

import domain.Cliente;
import domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//RestResource(collectionResourceRel = "cadcliente", path = "cadcliente")
public interface ClienteRepository extends CrudRepository<Cliente, String> {
    List<Cliente> findByEnderecoNacionalidade(@Param("nacionalidadenacionalidade") Country country);
}