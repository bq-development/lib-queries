package io.corbel.lib.queries.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The result of a Histogram aggregation is a List of HistogramValue
 *
 * @author Alexander De Leon
 */
public class HistogramResult extends ArrayList<HistogramResult.HistogramValue> implements AggregationResult {

    public HistogramResult(HistogramValue ... results){
        super(Arrays.asList(results));
    }

    public HistogramResult(List<HistogramValue> results){
        super(results);
    }

    /**
     * Defines a particular value for the field and the number of times (count) it occurs in the collection
     */
    public static class HistogramValue {
        private final Object id;
        private final long count;

        public HistogramValue(Object id,long count) {
            this.count = count;
            this.id = id;
        }

        public long getCount() {
            return count;
        }

        public Object getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof HistogramValue)) return false;

            HistogramValue that = (HistogramValue) o;

            if (count != that.count) return false;
            return !(id != null ? !id.equals(that.id) : that.id != null);

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (int) (count ^ (count >>> 32));
            return result;
        }
    }

}
