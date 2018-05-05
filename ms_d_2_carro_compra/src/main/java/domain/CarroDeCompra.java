package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@RedisHash("carrosdecompra")
@AllArgsConstructor
@NoArgsConstructor //CUIDADO!! FAZ DIFERENCA SE HOUVER OBJS RELACIONADOS!!
@Data
public class CarroDeCompra implements Serializable {
    private static final long serialVersionUID = -8760272805649222785L;

    @Id String idSessao;
    List<ItemCarroDeCompra> listaProdutoEscolhido;
    @Indexed String idCliente;

    Date dataCompra;
}
