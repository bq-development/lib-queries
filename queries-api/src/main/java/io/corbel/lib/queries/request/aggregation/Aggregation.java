package io.corbel.lib.queries.request.aggregation;

import io.corbel.lib.queries.request.ResourceQuery;

import java.util.List;

/**
 * @author Rubén Carrasco
 *
 */
public interface Aggregation {

    public List<ResourceQuery> operate(List<ResourceQuery> resourceQueries);

    public AggregationOperator getOperator();
}
