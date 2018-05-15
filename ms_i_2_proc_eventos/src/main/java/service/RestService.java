package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import domain.MediaMensalProduto;
import domain.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
/**
 * @author Luis
 * Classe principal Spring Boot.
 */
public class RestService {

    Runnable newThread = null;
    private static final Logger logger = LoggerFactory.getLogger(RestService.class);


    Map<Produto,Map<String, MediaMensalProduto>> mediasProd = new HashMap<>();


    @RequestMapping(value="/mediasProduto", method = RequestMethod.GET, produces = "application/json")
    public Map<String, MediaMensalProduto> getByTipo(@RequestParam(value="idproduto", defaultValue="") String produtoId) {
        System.out.println(" Metodo A"+produtoId);
        return mediasProd.get(new Produto(produtoId));
    }


    @RequestMapping("/pullProdutos")
    public String pull() {
        if (newThread == null){
            newThread = () -> getProdutosMQ();
            new Thread(newThread).start();
        }

        return "Iniciado o processo de pull da fila!";
    }


    private void getProdutosMQ()  {

        String exchange_name = "ex_direct";
        String exchange_type = "direct";
        String mq_container_hostname = "rabbitmq";
        String chave = "key_produto";
        String queueName = "queue_produto_cliente";

        boolean durableFlag = true;
        System.out.println(" Metodo B");

        try{
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
                    System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                    Produto produto = new ObjectMapper().readValue(message, Produto.class);
                    System.out.println(" [x - X] Mapeado '" + produto);

                }
            };
            channel.basicConsume(queueName, true, consumer);

        } catch (Exception e) {
            System.out.println("Falha : "+e.getCause());
            //ignored
        }

    }




}
