package hello.dao;

import hello.domain.Cliente;
import hello.domain.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestfulServiceProvider<E> {

    private static final Logger logger = LoggerFactory.getLogger(RestfulServiceProvider.class);

    RestTemplate template;

    public List<Cliente> fetchListForClientes(String url) {
        template = new RestTemplate();
        ResponseEntity<List<Cliente>> response =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Cliente>>() {
                        });
        template = null;
        return response.getBody();
    }


    public List<Produto> fetchListForProdutos(String url) {

        template = new RestTemplate();

        ResponseEntity<List<Produto>> response =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Produto>>() {
                        });
        template = null;

        System.out.print(response.getBody());
        return response.getBody();

/*
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        messageConverter.setObjectMapper(mapper);


        template.setMessageConverters(Arrays.asList(messageConverter));
        ResponseEntity<List<Resource<Produto>>> rateResponse2 =
                template.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Resource<Produto>>>() {
                        });

        System.out.println("Response 2"+rateResponse2);
        System.out.println("Response 2.1"+rateResponse2.getBody());
        return rateResponse2.getBody().stream().map(produtoResource ->
                produtoResource.getContent()).collect(Collectors.toList());
*/

    }
}
