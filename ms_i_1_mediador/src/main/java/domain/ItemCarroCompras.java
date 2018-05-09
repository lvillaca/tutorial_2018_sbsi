package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCarroCompras {
    @JsonIgnore Produto produto;
    int  qtdProduto;
    float valorUnitario;

    @JsonSetter("idProdutoEscolhido")
    public void setProduto(String idProdutoEscolhido) {
        this.produto = new Produto();
        produto.setId(idProdutoEscolhido);
    }

    public void refreshProduto(Produto outraInst) {
        this.produto.setFornecedor(outraInst.getFornecedor());
        this.produto.setModelo(outraInst.getModelo());
        this.produto.setTipo(outraInst.getTipo());
    }

}
