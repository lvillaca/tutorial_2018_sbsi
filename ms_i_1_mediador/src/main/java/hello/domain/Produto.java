package hello.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    String id; //nao mapeado no Schema
    Fornecedor fornecedor;
    String modelo;
    String tipo;
    public Produto(String id) {
        this.id = id;
    }
}