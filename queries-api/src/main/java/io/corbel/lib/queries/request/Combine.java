package io.corbel.lib.queries.request;

/**
 * @author Rubén Carrasco
 *
 */
public class Combine extends FieldAggregation {

    private final String expression;

    public Combine(String field, String expression) {
        super(field);
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public AggregationOperator getOperator() {
        return AggregationOperator.$COMBINE;
    }
}
