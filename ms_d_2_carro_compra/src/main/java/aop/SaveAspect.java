package aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import domain.CarroDeCompra;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SaveAspect {

    @Pointcut("execution(* boot.dao.CarroDeCompraRepository.save(*))")
    public void saveCapture() {
    }

    @Around("saveCapture()")
    public Object changeEvent(final ProceedingJoinPoint pJP) throws Throwable {
        Object[] args = pJP.getArgs();
        CarroDeCompra carroIdentificado = (CarroDeCompra) args[0];
        ObjectMapper mapperToJson = new ObjectMapper();
        String jsonCarro = mapperToJson.writeValueAsString(carroIdentificado);
        System.out.println("AT ASPECT - class:" + jsonCarro);
        publish(jsonCarro);

        return pJP.proceed(args);
    }

    public void publish(String jsonMsg) {

        String exchange_name = "ex_direct";
        String exchange_type = "direct";
        String mq_container_hostname = "rabbitmq";
        String chave = "key_carro";
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