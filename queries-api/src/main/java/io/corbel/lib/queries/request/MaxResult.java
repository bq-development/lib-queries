package io.corbel.lib.queries.request;

/**
 * @author Rubén Carrasco
 *
 */
public class MaxResult implements AggregationResult {
    private Object max;

    public MaxResult(Object max) {
        super();
        this.max = max;
    }

    public Object getMax() {
        return max;
    }

    public void setMax(Object max) {
        this.max = max;
    }
}
