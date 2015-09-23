package io.corbel.lib.queries.request.aggregation;


public class Average extends FieldAggregation {

    public Average(String field) {
        super(field);
    }

    @Override
    public AggregationOperator getOperator() {
        return AggregationOperator.$AVG;
    }

}
