package io.corbel.lib.queries.parser;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.Aggregation;

/**
 * @author Rubén Carrasco
 *
 */
public interface AggregationParser {

    Aggregation parse(String aggregation) throws MalformedJsonQueryException;

}
