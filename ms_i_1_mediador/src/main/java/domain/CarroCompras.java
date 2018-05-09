package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarroCompras {
    @JsonIgnore Cliente cliente;
    Date dataCompra;
    List<ItemCarroCompras> listaProdutoEscolhido;

    @JsonSetter("idCliente")
    public void setCliente(String idCliente) {
        this.cliente = new Cliente(idCliente);
    }

}
