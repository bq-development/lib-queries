package io.corbel.lib.queries.parser;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.aggregation.Aggregation;

/**
 * @author Rubén Carrasco
 *
 */
public interface AggregationParser {

	public Aggregation parse(String aggregationString) throws MalformedJsonQueryException;

}
