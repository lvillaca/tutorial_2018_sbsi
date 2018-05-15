package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produto implements Serializable {
    private static final long serialVersionUID = -8760272805649222785L;

    String idProduto;
    String modelo;
    Fornecedor fornecedor;
    TipoProduto tipo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(idProduto, produto.idProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto);
    }

    public Produto(String idProduto) {
        this.idProduto = idProduto;
    }
}
