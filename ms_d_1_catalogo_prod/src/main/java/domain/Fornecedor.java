package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1693062795543455614L;

    String nome;
    @Indexed Country pais;
}
