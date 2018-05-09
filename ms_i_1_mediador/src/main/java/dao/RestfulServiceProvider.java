package dao;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.CarroCompras;
import domain.Cliente;
import domain.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RestfulServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(RestfulServiceProvider.class);

    RestTemplate template;

    public List<Cliente> fetchListForClientes(String url) {
        template = new RestTemplate();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        messageConverter.setObjectMapper(mapper);


        template.setMessageConverters(Arrays.asList(messageConverter));
        ResponseEntity<Resources<Resource<Cliente>>> rateResponse2 =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<Cliente>>>() {
                        });
        return rateResponse2.getBody().getContent().stream().map(clienteResource ->
                clienteResource.getContent()).collect(Collectors.toList());

    }


    public List<Produto> fetchListForProdutos(String url) {

        template = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        messageConverter.setObjectMapper(mapper);


        template.setMessageConverters(Arrays.asList(messageConverter));
        ResponseEntity<Resources<Resource<Produto>>> rateResponse2 =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<Produto>>>() {
                        });
        return rateResponse2.getBody().getContent().stream().map(produtoResource ->
                produtoResource.getContent()).collect(Collectors.toList());

    }

    public List<CarroCompras> fetchListForCarroCompras(String url) {

        template = new RestTemplate();

        ResponseEntity<List<CarroCompras>> response =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<CarroCompras>>() {
                        });
        template = null;
        return response.getBody();
    }

    public Cliente fetchListForCliente(String url) {

        template = new RestTemplate();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        messageConverter.setObjectMapper(mapper);


        template.setMessageConverters(Arrays.asList(messageConverter));
        ResponseEntity<Resource<Cliente>> rateResponse2 =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Cliente>>() {
                        });
        return rateResponse2.getBody().getContent();

    }

    public Produto fetchListForProduto(String url) {

        template = new RestTemplate();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        messageConverter.setObjectMapper(mapper);


        template.setMessageConverters(Arrays.asList(messageConverter));
        ResponseEntity<Resource<Produto>> rateResponse2 =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Produto>>() {
                        });
        return rateResponse2.getBody().getContent();
    }


}
