package aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import domain.Cliente;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SaveAspect {

    private static final Logger logger = LoggerFactory.getLogger(SaveAspect.class);
    
    private Connection connection = null;
    private Channel channel = null;
    final String exchange_name = "ex_direct";

    @After("execution(* boot.dao.ClienteRepository.save(*))")
    public void changeEvent(final JoinPoint pJP) throws Throwable {
        //captura cliente alterado
        Object[] args = pJP.getArgs();
        Cliente clienteIdentificado = (Cliente) args[0];
        ObjectMapper mapperToJson = new ObjectMapper();
        String jsonCliente = mapperToJson.writeValueAsString(clienteIdentificado);
        logger.debug("EVENTO CAPTURADO:" + jsonCliente);
        publish(jsonCliente);
    }

    private void conectaMQ() {
        String exchange_type = "direct";
        String mq_container_hostname = "rabbitmq";
        boolean durableFlag = true;
        ConnectionFactory factory = null;
        try {
            factory = new ConnectionFactory();
            factory.setHost(mq_container_hostname);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(exchange_name, exchange_type, durableFlag);
        } catch (Exception e) {
            logger.debug("Erro na conexao:" + e.getMessage());
            try {
                channel.close();
                connection.close();
            } catch (Exception eClose) {
                //ignore
            }
        }
    }

    public void publish(String jsonMsg) {
        try {
            conectaMQ();
            String chave = "key_cliente";
            channel.basicPublish(exchange_name, chave, null, jsonMsg.getBytes());
            logger.debug(" [x] Sent '" + chave + "':'" + jsonMsg + "'");
        } catch (Exception e) {
            logger.debug("Erro na publicacao:" + e.getMessage());
        } finally {
            try {
                channel.close();
                connection.close();
            } catch (Exception e) {
                //ignore
            }
        }
    }
}