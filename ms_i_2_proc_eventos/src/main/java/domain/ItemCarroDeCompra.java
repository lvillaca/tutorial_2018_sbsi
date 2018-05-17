package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class ItemCarroDeCompra {

    String idProdutoEscolhido;
    int qtdProduto;
    long valorUnitario;
}

