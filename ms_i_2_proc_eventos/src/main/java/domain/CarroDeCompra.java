package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarroDeCompra {

    List<ItemCarroDeCompra> listaProdutoEscolhido;

    String idCliente;

    Date dataCompra;
}

