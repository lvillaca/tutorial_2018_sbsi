package hello.dao;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.domain.CarroCompras;
import hello.domain.Cliente;
import hello.domain.Produto;
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

public class RestfulServiceProvider<E> {

    private static final Logger logger = LoggerFactory.getLogger(RestfulServiceProvider.class);

    RestTemplate template;

    public List<Cliente> fetchListForClientes(String url) {

/*        template = new RestTemplate();
        ResponseEntity<List<Cliente>> response =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Cliente>>() {
                        });
        template = null;

        return response.getBody();*/

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

        System.out.println("Response 2"+rateResponse2);
        System.out.println("Response 2.1"+rateResponse2.getBody());
        return rateResponse2.getBody().getContent().stream().map(clienteResource ->
                clienteResource.getContent()).collect(Collectors.toList());

    }


    public List<Produto> fetchListForProdutos(String url) {

        template = new RestTemplate();

/*        ResponseEntity<List<Produto>> response =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Produto>>() {
                        });
        template = null;

        System.out.print(response.getBody());
        return response.getBody();*/


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

        System.out.println("Response 2"+rateResponse2);
        System.out.println("Response 2.1"+rateResponse2.getBody());
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

        System.out.print(response.getBody());
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

        System.out.println("Response 2"+rateResponse2);
        System.out.println("Response 2.1"+rateResponse2.getBody());
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

        System.out.println("Response 2"+rateResponse2);
        System.out.println("Response 2.1"+rateResponse2.getBody());
        return rateResponse2.getBody().getContent();

    }


}
