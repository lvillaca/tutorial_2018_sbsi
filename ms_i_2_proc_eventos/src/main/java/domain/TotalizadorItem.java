package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalizadorItem {
    long totalCompra;
    int quantidade;
}
