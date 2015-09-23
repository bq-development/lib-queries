package io.corbel.lib.queries.request;


public class Average extends FieldAggregation {

    public Average(String field) {
        super(field);
    }

    @Override
    public AggregationOperator getOperator() {
        return AggregationOperator.$AVG;
    }

}
