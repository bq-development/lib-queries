package io.corbel.lib.queries.request.aggregation;

/**
 * @author Rubén Carrasco
 *
 */
public class Min extends FieldAggregation {

    public Min(String field) {
        super(field);
    }

    @Override
    public AggregationOperator getOperator() {
        return AggregationOperator.$MIN;
    }

}
