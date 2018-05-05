package hello;

import hello.datafetcher.*;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
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
import java.net.URL;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@RestController
public class QueryMediator {
    private static final Logger logger = LoggerFactory.getLogger(QueryMediator.class);

    @Value("classpath:/schema.grasphqls")
    private Resource schemaResource;

    private GraphQL graphQLInstance;

    /*@Autowired
    private BandaDataFetcher bandaDataFetcher;
    @Autowired
    private ArtistaDataFetcher artistaDataFetcher;
    @Autowired
    private BandaArtistaDataFetcher bandaArtistaDataFetcher;
    @Autowired
    private ArtistaBandaDataFetcher artistaBandaDataFetcher;*/
    @Autowired
    private ClienteDataFetcher clienteDataFetcher;
    @Autowired
    private ProdutoDataFetcher produtoDataFetcher;

    @PostConstruct
    public void loadSchema() throws IOException {
        //  logger.debug("=================================================");
        //  logger.debug("antes de abrir");
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
                        .dataFetcher("buscaProduto", produtoDataFetcher))
                .build();

        /*return newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("buscaBanda", bandaDataFetcher)
                        .dataFetcher("buscaArtista", artistaDataFetcher))
                .type("Banda", builder -> builder
                        .dataFetcher("artistas", bandaArtistaDataFetcher))
                .type("Artista", builder -> builder
                        .dataFetcher("bandas", artistaBandaDataFetcher))
                .build();*/
    }

    @CrossOrigin
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity executeQuery(@RequestBody String queryParam) {

            //@RequestParam (value = "query",required=true, defaultValue = "{}")
                                                   //String queryParam) {
            //@RequestBody String queryParam) {
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

        logger.debug("query 1 :"+dataToQuery);

        if(dataToQuery==null) {
            dataToQuery=queryParam;
        }

        logger.debug("query 2 :"+dataToQuery);

        ExecutionResult executionResultOK = graphQLInstance.execute(dataToQuery);


        System.out.println("resultado encontrado!!"+executionResultOK.getData());
        System.out.println("erros encontrados!!"+executionResultOK.getErrors());

        return ResponseEntity.ok(executionResultOK.getData());//executionResult.getData().toString());
    }
}
