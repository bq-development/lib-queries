package io.corbel.lib.queries.parser;

import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.*;

/**
 * @author Rubén Carrasco
 *
 */
public class JacksonAggregationParser implements AggregationParser {

    private final CustomJsonParser jsonParser;

    public JacksonAggregationParser(CustomJsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    @Override
    public Aggregation parse(String aggregation) throws MalformedJsonQueryException {
        JsonNode node = jsonParser.readValueAsTree(aggregation);
        if (node.isObject()) {
            return getOperationFromNode(node);
        } else {
            throw new MalformedJsonQueryException("Unexpected array");
        }
    }

    private Aggregation getOperationFromNode(JsonNode node) throws MalformedJsonQueryException {
        Iterator<String> fieldNames = node.fieldNames();
        if (fieldNames.hasNext()) {
            String key = fieldNames.next();

            if (fieldNames.hasNext()) {
                throw new MalformedJsonQueryException("Wrong number of fields (Expected one)");
            }


            JsonNode value = node.get(key);
            switch (getOperator(key)) {
                case $COUNT:
                    return new Count(value.asText());
                case $AVG:
                    return new Average(value.asText());
                case $SUM:
                    return new Sum(value.asText());
                case $MAX:
                    return new Max(value.asText());
                case $MIN:
                    return new Min(value.asText());
                case $COMBINE:
                    Map.Entry<String, JsonNode> entry = value.fields().next();
                    return new Combine(entry.getKey(), entry.getValue().asText());
                case $HISTOGRAM:
                    return new Histogram(value.asText());
                default:
                    return null;
            }

        } else {
            throw new MalformedJsonQueryException("Empty object");
        }
    }

    private AggregationOperator getOperator(String key) throws MalformedJsonQueryException {
        try {
            return AggregationOperator.valueOf(key.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MalformedJsonQueryException("Unknown aggregation operator " + key);
        }
    }

}
