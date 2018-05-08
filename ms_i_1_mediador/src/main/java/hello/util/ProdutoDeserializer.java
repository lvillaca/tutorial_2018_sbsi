/*
package hello.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import hello.domain.Produto;

import java.io.IOException;

public class ProdutoDeserializer extends StdDeserializer<Produto> {

    public ProdutoDeserializer() {
        this(null);
    }

    public ProdutoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Produto deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String   idProduto = node.get("idProdutoEscolhido").asText();

        return new Produto(idProduto, null, null,null);
    }
}
*/
