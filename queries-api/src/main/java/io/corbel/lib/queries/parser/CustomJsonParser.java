package io.corbel.lib.queries.parser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.corbel.lib.queries.exception.MalformedJsonQueryException;

/**
 * @author Rubén Carrasco
 *
 */
public class CustomJsonParser {

    private final JsonFactory jsonFactory;

    public CustomJsonParser(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public JsonNode readValueAsTree(String json) throws MalformedJsonQueryException {
        try {
            return jsonFactory.createParser(json).readValueAsTree();
        } catch (JsonProcessingException e) {
            throw new MalformedJsonQueryException("Json parse exception", e);
        } catch (IOException e) {
            throw new MalformedJsonQueryException("IOexception", e);
        }
    }
}
