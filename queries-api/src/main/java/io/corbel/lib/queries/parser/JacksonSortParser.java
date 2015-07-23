package io.corbel.lib.queries.parser;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.Sort;
import com.fasterxml.jackson.databind.JsonNode;

import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author by Francisco Sanchez
 */
public class JacksonSortParser implements SortParser {
    private static final Logger LOG = LoggerFactory.getLogger(JacksonSortParser.class);

    private final CustomJsonParser jsonParser;

    public JacksonSortParser(CustomJsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }


    @Override
    public Sort parse(String json) throws MalformedJsonQueryException {
        try {
            JsonNode parsedJson = jsonParser.readValueAsTree(json);
            if (!parsedJson.isObject() || parsedJson.size() != 1) {
                throw new IllegalArgumentException("Sort is only allowed on one field. You gave me " + parsedJson.size());
            }

            Map.Entry<String, JsonNode> entry = parsedJson.fields().next();
            String orderType = entry.getValue().asText();
            String field = entry.getKey();
            return new Sort(orderType, field);
        } catch (JsonParseException | IllegalStateException | UnsupportedOperationException e) {
            LOG.debug("Invalid sort string: {}. Throwing IllegalArgumentException", json);
            throw new IllegalArgumentException("Not a valid sort string: " + json);
        }
    }
}
