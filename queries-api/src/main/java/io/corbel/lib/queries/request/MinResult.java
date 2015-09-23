package io.corbel.lib.queries.request;

/**
 * @author Rubén Carrasco
 *
 */
public class MinResult implements AggregationResult {
    private Object min;

    public MinResult(Object min) {
        super();
        this.min = min;
    }

    public Object getMax() {
        return min;
    }

    public void setMax(Object min) {
        this.min = min;
    }
}
