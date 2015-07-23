package io.corbel.lib.queries.request;

import java.util.List;

import io.corbel.lib.queries.BooleanQueryLiteral;
import io.corbel.lib.queries.QueryNodeImpl;

/**
 * @author Rubén Carrasco
 *
 */
public class Count extends FieldAggregation {

    private static final String ALL = "*";

    public Count(String field) {
        super(field);
    }

    @Override
    public List<ResourceQuery> operate(List<ResourceQuery> resourceQueries) {
        resourceQueries = super.operate(resourceQueries);
        if (!field.equals(ALL)) {
            BooleanQueryLiteral literal = new BooleanQueryLiteral();
            literal.setLiteral(true);
            QueryNodeImpl queryNode = new QueryNodeImpl(QueryOperator.$EXISTS, field, literal);

            for (ResourceQuery resourceQuery : resourceQueries) {
                resourceQuery.addQueryNode(queryNode);
            }
        }
        return resourceQueries;
    };

    @Override
    public AggregationOperator getOperator() {
        return AggregationOperator.$COUNT;
    }
}
