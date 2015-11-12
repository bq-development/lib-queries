package io.corbel.lib.queries.request;

import com.google.gson.JsonObject;

import java.util.List;

public class CombineResult implements AggregationResult {

    private List<JsonObject> results;

    public CombineResult() {}

    public CombineResult(List<JsonObject> results) {
        this.results = results;
    }

    public List<JsonObject> getResults() {
        return results;
    }

    public void setResults(List<JsonObject> results) {
        this.results = results;
    }
}
