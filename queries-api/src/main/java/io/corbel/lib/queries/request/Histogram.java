package io.corbel.lib.queries.request;

/**
 * A histogram aggregation is a list of the distinct values of a given field and the count of its occurrences.
 *
 * @see HistogramResult
 *
 * @author Alexander De Leon <alex.deleon@devialab.com>
 */
public class Histogram extends FieldAggregation {

    public Histogram(String field) {
        super(field);
    }

    @Override
    public AggregationOperator getOperator() {
        return AggregationOperator.$HISTOGRAM;
    }
}
