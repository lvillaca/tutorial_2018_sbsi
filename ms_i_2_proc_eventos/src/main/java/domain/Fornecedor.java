package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1693062795543455614L;

    String nome;
    Country pais;
}
