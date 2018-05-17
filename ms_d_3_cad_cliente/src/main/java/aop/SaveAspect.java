package aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import domain.Cliente;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SaveAspect {

    @Pointcut("execution(* boot.dao.ClienteRepository.save(*))")
    public void saveCapture() {
    }

    @Around("saveCapture()")
    public Object changeEvent(final ProceedingJoinPoint pJP) throws Throwable {
        Object[] args = pJP.getArgs();
        Cliente clienteIdentificado = (Cliente) args[0];
        ObjectMapper mapperToJson = new ObjectMapper();
        String jsonCliente = mapperToJson.writeValueAsString(clienteIdentificado);
        System.out.println("AT ASPECT - class:" + jsonCliente);
        publish(jsonCliente);

        return pJP.proceed(args);
    }

    public void publish(String jsonMsg) {

        String exchange_name = "ex_direct";
        String exchange_type = "direct";
        String mq_container_hostname = "rabbitmq";
        String chave = "key_cliente";
        boolean durableFlag = true;

        ConnectionFactory factory = null;
        Connection connection = null;
        Channel channel = null;
        try {

            factory = new ConnectionFactory();
            factory.setHost(mq_container_hostname);

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(exchange_name, exchange_type, durableFlag);
            channel.basicPublish(exchange_name, chave, null, jsonMsg.getBytes());
            System.out.println(" [x] Sent '" + chave + "':'" + jsonMsg + "'");

        } catch (Exception e) {
            System.out.println("Erro na publicacao:" + e.getMessage());
            System.out.println("Erro na publicacao:" + e.getCause());
            System.out.println("Erro na publicacao:" + e.getStackTrace());
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
