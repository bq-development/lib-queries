package com.bq.oss.lib.queries.request;

/**
 * @author Rubén Carrasco
 *
 */
public interface Aggregation {

	public ResourceQuery operate(ResourceQuery resourceQuery);

	public AggregationOperator getOperator();
}
