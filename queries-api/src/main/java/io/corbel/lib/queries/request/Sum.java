package io.corbel.lib.queries.request;


public class Sum extends FieldAggregation {

    public Sum(String field) {
        super(field);
    }

    @Override
    public AggregationOperator getOperator() {
        return AggregationOperator.$SUM;
    }

}
