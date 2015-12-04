package io.corbel.lib.queries.request;

import com.google.gson.*;
import java.util.Optional;

/**
 * This class offers factory methods for all aggregations results
 *
 * @author Alexander De Leon <alex.deleon@devialab.com>
 */
public class JsonAggregationResultsFactory implements AggregationResultsFactory<JsonElement> {

    private static final String AVERAGE = "average";
    private static final String COUNT = "count";
    private static final String MAX = "max";
    private static final String MIN = "min";
    private static final String SUM = "sum";

    private Gson gson = new Gson();

    public JsonAggregationResultsFactory(Gson gson){
        this.gson = gson;
    }

    public JsonAggregationResultsFactory() {
        this(new Gson());
    }

    @Override
    public JsonObject averageResult(Optional<Double> average) {
        JsonElement value =  average.map(JsonPrimitive::new).orElse(null);
        return json(new JsonField(AVERAGE, value));
    }

    /* count should never be empty */
    @Override
    public JsonObject countResult(long count) {
        JsonElement value =  new JsonPrimitive(count);
        return json(new JsonField(COUNT, value));
    }

    @Override
    public JsonObject maxResult(Optional<Object> max) {
        JsonElement value =  max.map(gson::toJsonTree).orElse(null);
        return json(new JsonField(MAX, value));
    }

    @Override
    public JsonObject minResult(Optional<Object> min) {
        JsonElement value =  min.map(gson::toJsonTree).orElse(null);
        return json(new JsonField(MIN, value));
    }

    @Override
    public JsonObject sumResult(Optional<Double> sum) {
        JsonElement value =  sum.map(JsonPrimitive::new).orElse(null);
        return json(new JsonField(SUM, value));
    }

    @Override
    public JsonArray histogramResult(HistogramEntry... entries) {
        return gson.toJsonTree(entries).getAsJsonArray();
    }

    /**
     * Create a JsonObject with the specified fields
     */
    private JsonObject json(JsonField... fields){
        JsonObject json = new JsonObject();
        for(JsonField field : fields){
            json.add(field.key, field.value);
        }
        return json;
    }

    /** Helper class for in-line json creation */
    private class JsonField {
        final String key;
        final JsonElement value;
        public JsonField(String key, JsonElement value){
            this.key = key;
            this.value = value;
        }
    }

}
