package io.corbel.lib.queries.request.aggregation;

/**
 * @author Rubén Carrasco
 *
 */
public class Max extends FieldAggregation {

    public Max(String field) {
        super(field);
    }

    @Override
    public AggregationOperator getOperator() {
        return AggregationOperator.$MAX;
    }

}
