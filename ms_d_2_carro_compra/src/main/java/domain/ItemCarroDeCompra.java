package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ItemCarroDeCompra implements Serializable {
    private static final long serialVersionUID = -5346744648707032861L;

    @Indexed String idProdutoEscolhido;
    int qtdProduto;
    BigDecimal valorUnitario;
}
