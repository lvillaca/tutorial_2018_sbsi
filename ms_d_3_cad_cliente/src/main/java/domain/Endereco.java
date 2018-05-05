package domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
public class Endereco {

    @GeneratedValue
    @Id
    private int idEndereco;

    private String nome;
    private Country nacionalidade;
}