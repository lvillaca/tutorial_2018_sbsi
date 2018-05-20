package report;

import domain.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Processamento {
    final TreeMap<String,TreeMap<ChaveTotalMensal, TotalizadorItem>> mapaOrdenadoMediaModelo = new TreeMap<>();
    final Map<String, Produto> mapaProdutos = new HashMap<>();
    final Map<String, Cliente> mapaClientes = new HashMap<>();

    public TreeMap<String,TreeMap<ChaveTotalMensal, TotalizadorItem>> getMapaOrdenadoMediaModelo() {
        return mapaOrdenadoMediaModelo;
    }

    /**
     * Atualiza produto a partir de evento
     * @param produtoAtualizado
     */
    public void atualizaProduto(Produto produtoAtualizado) {
        mapaProdutos.put(produtoAtualizado.getIdProduto(),produtoAtualizado);
    }

    /**
     * Atualiza produto a partir de evento
     * @param clienteAtualizado
     */
    public void atualizaCliente(Cliente clienteAtualizado) {
        mapaClientes.put(clienteAtualizado.getIdCliente(),clienteAtualizado);
    }

    /**
     * Atualiza produto a partir de evento
     * @param carroDeCompraAtualizado
     */
    public void atualizaCarro(CarroDeCompra carroDeCompraAtualizado) {
        //primeiro atualizo mapa de media mensal por modelo
        atualizaMapaMediaMensalModelo(carroDeCompraAtualizado);

        //depois atualizo mapa de media mensal por pais cliente
    }

    /**
     * Atualiza mapa de media mensal por modelo
     * @param carroDeCompraAtualizado
     */
    public void atualizaMapaMediaMensalModelo(CarroDeCompra carroDeCompraAtualizado) {

        Date dataCompra = carroDeCompraAtualizado.getDataCompra();

        //para cada item de compra..
        carroDeCompraAtualizado.getListaProdutoEscolhido().forEach(itemCarroDeCompra ->
        {
            Produto produtoCarroDeCompra = mapaProdutos.get(itemCarroDeCompra.getIdProdutoEscolhido());

            if (!mapaOrdenadoMediaModelo.containsKey(produtoCarroDeCompra.getModelo())) {

                //1o caso - insercao - novo modelo
                mapaOrdenadoMediaModelo.put(produtoCarroDeCompra.getModelo(), new TreeMap<>());

                //nesse caso - insercao - novo mes
                criaItemMensalModelo(itemCarroDeCompra,dataCompra,
                        mapaOrdenadoMediaModelo.get(produtoCarroDeCompra.getModelo()));

            } else {

                //2o caso - modelo existe, mas mes nao
                ChaveTotalMensal chaveTotalMensal = new ChaveTotalMensal(dataCompra);
                if (!mapaOrdenadoMediaModelo.get(produtoCarroDeCompra.getModelo()).containsKey(chaveTotalMensal)) {

                    //nesse caso - insercao - novo mes
                    criaItemMensalModelo(itemCarroDeCompra,dataCompra,
                            mapaOrdenadoMediaModelo.get(produtoCarroDeCompra.getModelo()));
                }

                else {

                    //3o caso - modelo e mes existem, refaz a media
                    TotalizadorItem totalizadorItemMensal =
                            mapaOrdenadoMediaModelo.get(produtoCarroDeCompra.getModelo()).get(chaveTotalMensal);

                    //atualiza Media
                    atualizaItemMensalModelo(totalizadorItemMensal,itemCarroDeCompra);
                }
            }

        });



    }

    /**
     * Gera novo item mensal.
     *
     * @param itemCarroDeCompra
     * @param dataCompra
     * @param mapa
     */
    public void criaItemMensalModelo(ItemCarroDeCompra itemCarroDeCompra, Date dataCompra, TreeMap<ChaveTotalMensal, TotalizadorItem> mapa) {
        TotalizadorItem itemMensal = new TotalizadorItem(
                 itemCarroDeCompra.getValorUnitario()*itemCarroDeCompra.getQtdProduto(),
                itemCarroDeCompra.getQtdProduto());
        ChaveTotalMensal mediaMensal = new ChaveTotalMensal(dataCompra);
        mapa.put(mediaMensal, itemMensal);
    }

    /**
     * Altera item mensal existente.
     *
     * @param totalizadorItemMensal
     * @param itemCarroDeCompra
     */
    public void atualizaItemMensalModelo(TotalizadorItem totalizadorItemMensal, ItemCarroDeCompra itemCarroDeCompra) {
        int quantidadeAntes = totalizadorItemMensal.getQuantidade();
        long totalAntes = totalizadorItemMensal.getTotalCompra();
        totalizadorItemMensal.setTotalCompra(
                totalAntes +itemCarroDeCompra.getValorUnitario()*itemCarroDeCompra.getQtdProduto()
        );
        totalizadorItemMensal.setQuantidade(quantidadeAntes+itemCarroDeCompra.getQtdProduto());
    }

    /**
     * Calcula e retorna a media de cada modelo
     * @return
     */
    public Map<String, Double> getMediasModelo() {

        Map<java.lang.String, java.lang.Double> medias = new HashMap<>();
        mapaOrdenadoMediaModelo.
                forEach((modelo, mapaProds) -> {
                    medias.put(modelo,
                            mapaProds.descendingKeySet().stream().limit(12).mapToDouble(
                                    chaveTotalMensal -> mapaProds.get(chaveTotalMensal).getTotalCompra()
                            ).average().getAsDouble());
                });
        return medias;
    }

    /**
     * Calcula e retorna a media de cada modelo
     * @return Map contendo total de 12 ultimos meses
     */
    public Map<java.lang.String, Map<ChaveTotalMensal, TotalizadorItem>> getTotaisModeloDozeMeses() {

        Map<java.lang.String, Map<ChaveTotalMensal, TotalizadorItem>> totais = new TreeMap();

        mapaOrdenadoMediaModelo.
                forEach((modelo, mapaProds) -> {
                            Map<ChaveTotalMensal, TotalizadorItem> meses =
                                    mapaProds.descendingKeySet().stream().limit(12).collect(Collectors.toMap(
                                            chave -> chave, chave -> mapaOrdenadoMediaModelo.get(modelo).get(chave)));
                            ///// sort reverse
                            Map<ChaveTotalMensal, TotalizadorItem> mesesSort = new TreeMap<>(
                                    new Comparator<ChaveTotalMensal>() {
                                @Override
                                public int compare(ChaveTotalMensal chaveTotalMensal, ChaveTotalMensal t1) {
                                    if (t1.getPosicao() == chaveTotalMensal.getPosicao()) return 0;
                                    if (t1.getPosicao() > chaveTotalMensal.getPosicao()) return 1;
                                    else return -1;
                                }


                            });
                            mesesSort.putAll(meses);

                            totais.put(modelo, mesesSort);
                        }
                );
        return totais;
    }

}

