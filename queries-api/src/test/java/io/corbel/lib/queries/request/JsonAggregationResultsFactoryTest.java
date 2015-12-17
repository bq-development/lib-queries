package io.corbel.lib.queries.request;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * @author Alexander De Leon
 */
public class JsonAggregationResultsFactoryTest {

    private final JsonAggregationResultsFactory factory = new JsonAggregationResultsFactory();

    @Test
    public void testAverageResult() {
        JsonObject result = factory.averageResult(Optional.of(2.1));
        assertThat(result.get("average")).isEqualTo(new JsonPrimitive(2.1d));
    }

    @Test
    public void testEmptyAverageResult() {
        JsonObject result = factory.averageResult(Optional.empty());
        assertThat(result.get("average").isJsonNull()).isTrue();
    }

    @Test
    public void testCountResult() {
        JsonObject result = factory.countResult(100l);
        assertThat(result.get("count")).isEqualTo(new JsonPrimitive(100));
    }

    @Test
    public void testMaxResult() {
        JsonObject result = factory.maxResult(Optional.of("Hello"));
        assertThat(result.get("max")).isEqualTo(new JsonPrimitive("Hello"));
    }

    @Test
    public void testEmptyMaxResult() {
        JsonObject result = factory.maxResult(Optional.empty());
        assertThat(result.get("max").isJsonNull()).isTrue();
    }

    @Test
    public void testMinResult() {
        JsonObject result = factory.minResult(Optional.of("Hello"));
        assertThat(result.get("min")).isEqualTo(new JsonPrimitive("Hello"));
    }

    @Test
    public void testEmptyMinResult() {
        JsonObject result = factory.minResult(Optional.empty());
        assertThat(result.get("min").isJsonNull()).isTrue();
    }

    @Test
    public void testSumResult() {
        JsonObject result = factory.sumResult(Optional.of(3.3d));
        assertThat(result.get("sum")).isEqualTo(new JsonPrimitive(3.3d));
    }

    @Test
    public void testEmptySumResult() {
        JsonObject result = factory.sumResult(Optional.empty());
        assertThat(result.get("sum").isJsonNull()).isTrue();
    }

    @Test
    public void testHistogramResult() {
        JsonArray result = factory.histogramResult(new HistogramEntry(1, Collections.singletonMap("id", "a")), new HistogramEntry(2,
                Collections.singletonMap("id", "b")));
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getAsJsonObject().get("values").getAsJsonObject().get("id")).isEqualTo(new JsonPrimitive("a"));
        assertThat(result.get(0).getAsJsonObject().get("count")).isEqualTo(new JsonPrimitive(1));
        assertThat(result.get(1).getAsJsonObject().get("values").getAsJsonObject().get("id")).isEqualTo(new JsonPrimitive("b"));
        assertThat(result.get(1).getAsJsonObject().get("count")).isEqualTo(new JsonPrimitive(2));
    }

}