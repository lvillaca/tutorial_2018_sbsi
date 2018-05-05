package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cliente  {

    @Id
    String idCliente;

    String nome;
    @OneToOne(cascade = {CascadeType.ALL}) Endereco endereco;
}
