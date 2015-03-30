package com.bq.oss.lib.queries.parser;

import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import com.bq.oss.lib.queries.request.Aggregation;

/**
 * @author Rubén Carrasco
 *
 */
public interface AggregationParser {

	public Aggregation parse(String aggregationString) throws MalformedJsonQueryException;

}
