package io.corbel.lib.queries.request;

import java.util.Map;

/**
 * Defines a particular count for a tuple of values that occurs in the collection
 */
public class HistogramEntry {
    private final Map<String,Object> values;
    private final long count;

    public HistogramEntry(long count, Map<String,Object> values) {
        this.count = count;
        this.values = values;
    }

    public long getCount() {
        return count;
    }

    public Map<String, Object> getValues() {
        return values;
    }
}
