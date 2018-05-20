package domain;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class ChaveTotalMensal implements Comparable<ChaveTotalMensal>{
    int ano;
    int mes;

    /**
     * Construtor a partir da data de compra
     * @param dataCompra
     */
    public ChaveTotalMensal(Date dataCompra) {
        LocalDate guia = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dataCompra));
        ano = guia.getYear();
        mes = guia.getMonthValue();
    }

    /**
     * Getter para Comparator
     * @return
     */
    public int getPosicao() {
        return ano*100+mes;
    }

    /**
     * ToString para mostrar nome do mes e ano
     */
    @Override
    public String toString() {
        return Meses.values()[mes-1] + " - " + ano;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        ChaveTotalMensal that = (ChaveTotalMensal) o;
        return getPosicao() == that.getPosicao();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosicao());
    }

    @Override
    public int compareTo(ChaveTotalMensal chaveTotalMensal) {
        if (this.getPosicao() == chaveTotalMensal.getPosicao()) return 0;
        if (this.getPosicao() > chaveTotalMensal.getPosicao()) return 1;
        else return -1;
    }
}
