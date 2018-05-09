package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cliente {
    String id; //nao mapeado no Schema
    String nome;
    Endereco endereco;
    public Cliente(String id) {
        this.id = id;
    }
}