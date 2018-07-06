package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import report.Processamento;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@RestController
/**
 * @author Luis
 * Classe principal Spring Boot.
 */
public class RestService {

    @Autowired
    Processamento processamentoReport;

    private Runnable produtoClienteMQThread = null;
    private Runnable carroMQThread = null;

    private static final Logger logger = LoggerFactory.getLogger(RestService.class);



    @RequestMapping(value = "/totaisProdutos", method = RequestMethod.GET, produces = "application/json")
    public TreeMap<String,TreeMap<ChaveTotalMensal, TotalizadorItem>> getReportTotaisModelo() {
        return processamentoReport.getMapaOrdenadoTotalizadorModelo();
    }

    @RequestMapping(value = "/totaisProdutosUltimosDozeMeses", method = RequestMethod.GET, produces = "application/json")
    public Map<java.lang.String, Map<ChaveTotalMensal, TotalizadorItem>> getReportTotaisModeloDoze() {
        logger.debug(" Metodo Totais 12 meses sort desc"+processamentoReport.getMapaOrdenadoTotalizadorModelo());
        return processamentoReport.getTotaisModeloDozeMeses();
    }



    @RequestMapping(value = "/mediasProdutos", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> getReportMediasModelo() {
        logger.debug(" Metodo Media");
        return processamentoReport.getMediasModelo();
    }


    @RequestMapping("/pullProdutosClientes")
    public String pullProdutosClientes() {
        if ( produtoClienteMQThread == null ) {
            produtoClienteMQThread = () -> getProdutosClientesMQ();
            new Thread(produtoClienteMQThread).start();
        }
        return "Iniciado o processo de pull da fila de Produtos!";
    }

    @RequestMapping("/pullCarros")
    public String pullCarros() {
        if ( carroMQThread == null ) {
            carroMQThread = () -> getCarrosMQ();
            new Thread(carroMQThread).start();
        }
        return "Iniciado o processo de pull da fila de Carros!";
    }


    private void getProdutosClientesMQ() {

        String exchange_name = "ex_direct";
        String exchange_type = "direct";
        String mq_container_hostname = "rabbitmq";
        String queueName = "queue_produto_cliente";
        String chaveProduto = "key_produto";
        String chaveCliente = "key_cliente";

        boolean durableFlag = true;
        logger.debug(" MQ Listener - Produtos e Clientes");

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(mq_container_hostname);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchange_name, exchange_type, durableFlag);
            String qName = channel.queueDeclare().getQueue();
            channel.queueBind(qName, exchange_name, "");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    logger.debug(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                    if (envelope.getRoutingKey().equals(chaveProduto)) {
                        Produto produto = new ObjectMapper().readValue(message, Produto.class);
                        logger.debug(" [x - X] Mapeado '" + produto);
                        processamentoReport.atualizaProduto(produto);
                    }
                    if (envelope.getRoutingKey().equals(chaveCliente)) {
                        Cliente cliente = new ObjectMapper().readValue(message, Cliente.class);
                        logger.debug(" [x - X] Mapeado '" + cliente);
                        processamentoReport.atualizaCliente(cliente);
                    }
                }
            };
            channel.basicConsume(queueName, true, consumer);

        } catch (Exception e) {
            logger.debug("Falha : " + e.getCause());
            //ignored
        }
    }

    /**
     *
     */
    private void getCarrosMQ() {

        String exchange_name = "ex_direct";
        String exchange_type = "direct";
        String mq_container_hostname = "rabbitmq";
        String chave = "key_carro";
        String queueName = "queue_carro";

        boolean durableFlag = true;
        logger.debug(" MQ Listener - Carros de Compra");

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(mq_container_hostname);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchange_name, exchange_type, durableFlag);

            channel.queueBind(queueName, exchange_name, chave);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    logger.debug(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                    CarroDeCompra carro = new ObjectMapper().readValue(message, CarroDeCompra.class);
                    logger.debug(" [x - X] Mapeado '" + carro);
                    processamentoReport.atualizaCarro(carro);

                }
            };
            channel.basicConsume(queueName, true, consumer);

        } catch (Exception e) {
            logger.debug("Falha : " + e.getCause());
            //ignored
        }

    }


}
