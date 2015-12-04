package io.corbel.lib.queries.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Optional;

/**
 * @author Alexander De Leon <alex.deleon@devialab.com>
 */
public interface AggregationResultsFactory<T> {

    T averageResult(Optional<Double> average);

    /* count should never be empty */
    T countResult(long count);

    T maxResult(Optional<Object> max);

    T minResult(Optional<Object> min);

    T sumResult(Optional<Double> sum);

    T histogramResult(HistogramEntry... entries);
}
