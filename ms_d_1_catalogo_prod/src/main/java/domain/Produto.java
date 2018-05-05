package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produto implements Serializable {
    private static final long serialVersionUID = -8760272805649222785L;

    @Id String idProduto;
    String modelo;
    Fornecedor fornecedor;
    @Indexed TipoProduto tipo;
}
