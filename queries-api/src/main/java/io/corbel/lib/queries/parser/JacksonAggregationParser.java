package io.corbel.lib.queries.parser;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.Aggregation;
import io.corbel.lib.queries.request.AggregationOperator;
import io.corbel.lib.queries.request.Average;
import io.corbel.lib.queries.request.Count;
import io.corbel.lib.queries.request.Max;
import io.corbel.lib.queries.request.Min;
import io.corbel.lib.queries.request.Sum;

import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;

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
    public Aggregation parse(String aggregationString) throws MalformedJsonQueryException {
        JsonNode node = jsonParser.readValueAsTree(aggregationString);
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

            String textValue = node.get(key).textValue();
            switch (getOperator(key)) {
                case $COUNT:
                    return new Count(textValue);
                case $AVG:
                    return new Average(textValue);
                case $SUM:
                    return new Sum(textValue);
                case $MAX:
                    return new Max(textValue);
                case $MIN:
                    return new Min(textValue);
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
