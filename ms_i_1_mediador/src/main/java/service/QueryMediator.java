package service;

import datafetcher.*;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.SchemaPrinter;
import graphql.schema.idl.TypeDefinitionRegistry;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@RestController
public class QueryMediator {
    private static final Logger logger = LoggerFactory.getLogger(QueryMediator.class);

    @Value("classpath:/schema.grasphqls")
    private Resource schemaResource;

    private GraphQL graphQLInstance;

    @Autowired
    private ClienteDataFetcher clienteDataFetcher;
    @Autowired
    private ProdutoDataFetcher produtoDataFetcher;
    @Autowired
    private CarroDataFetcher carroDataFetcher;
    @Autowired
    private ClientePorCarroDataFetcher clientePorCarroDataFetcher;
    @Autowired
    private ComprasPorCarroDataFetcher comprasPorCarroDataFetcher;

    @PostConstruct
    public void loadSchema() throws IOException {

        logger.debug("==========================================");


        InputStreamReader schemaReader = new InputStreamReader(schemaResource.getInputStream());
        //File schemaFile = schemaResource.getFile();
        logger.debug("arquivo encontrado - "+schemaReader);
        TypeDefinitionRegistry typeDefinitionRegistry = (new SchemaParser()).parse(schemaReader);
        logger.debug("parser ok!!!"+typeDefinitionRegistry);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema graphQLSchema = (new SchemaGenerator()).makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        logger.debug("Schema validado"+new SchemaPrinter().print(graphQLSchema));
        graphQLInstance = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {

        return newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("buscaCliente", clienteDataFetcher)
                        .dataFetcher("buscaProduto", produtoDataFetcher)
                        .dataFetcher("buscaCarroCompras", carroDataFetcher))
                .type("CarroCompras", builder -> builder
                        .dataFetcher("cliente", clientePorCarroDataFetcher)
                        .dataFetcher("compras", comprasPorCarroDataFetcher))
                .build();
    }

    @CrossOrigin
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity executeQuery(@RequestBody String queryParam) {
        logger.debug("========================================");
        logger.debug("Chamada de executeQuery com req body:"+queryParam);

        String dataToQuery;

        try {

            JSONObject queryJson = new JSONObject(queryParam);
            dataToQuery = queryJson.getString("query");

            if (dataToQuery == null || dataToQuery.isEmpty()) {
                throw new JSONException("");
            }
        } catch (JSONException je) {

            dataToQuery = queryParam;
        }

        if(dataToQuery==null) {
            dataToQuery=queryParam;
        }

        logger.debug("query :"+dataToQuery);

        ExecutionResult executionResultOK = graphQLInstance.execute(dataToQuery);


        logger.debug("resultado encontrado!!"+executionResultOK.getData());
        logger.debug("erros encontrados!!"+executionResultOK.getErrors());

        return ResponseEntity.ok(executionResultOK.getData());
    }
}
