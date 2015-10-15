package io.corbel.lib.queries.jaxrs;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.corbel.lib.queries.request.*;


/**
 * @author Alexander De Leon
 */
public class QueryParameters {

    private Pagination pagination;
    private Optional<Sort> sort;
    private Optional<List<ResourceQuery>> queries;
    private Optional<List<ResourceQuery>> conditions;
    private Optional<Aggregation> aggregation;
    private Optional<Search> search;

    public QueryParameters(Pagination pagination, Optional<Sort> sort, Optional<List<ResourceQuery>> queries,
            Optional<List<ResourceQuery>> conditions, Optional<Aggregation> aggregation, Optional<Search> search) {
        this.pagination = pagination;
        this.sort = sort;
        this.queries = queries;
        this.conditions = conditions;
        this.aggregation = aggregation;
        this.search = search;
    }

    public QueryParameters(QueryParameters other) {
        this.pagination = other.pagination;
        this.sort = other.sort;
        this.queries = other.queries;
        this.conditions = other.conditions;
        this.aggregation = other.aggregation;
        this.search = other.search;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Optional<Sort> getSort() {
        return sort;
    }

    public void setSort(Optional<Sort> sort) {
        this.sort = sort;
    }

    @Deprecated
    public Optional<ResourceQuery> getQuery() {
        return Optional.ofNullable(queries.map(queries -> queries.get(0)).orElse(null));
    }

    public void setQuery(Optional<ResourceQuery> optionalQuery) {
        queries = Optional.ofNullable(optionalQuery.map(Arrays::asList).orElse(null));
    }

    public Optional<List<ResourceQuery>> getQueries() {
        return queries;
    }

    public void setQueries(Optional<List<ResourceQuery>> listQueries) {
        this.queries = listQueries;
    }

    public Optional<List<ResourceQuery>> getConditions() {
        return conditions;
    }

    public void setConditions(Optional<List<ResourceQuery>> conditions) {
        this.conditions = conditions;
    }

    public Optional<Search> getSearch() {
        return search;
    }

    public void setSearch(Optional<Search> search) {
        this.search = search;
    }

    public Optional<Aggregation> getAggregation() {
        return aggregation;
    }

    public void setAggregation(Optional<Aggregation> aggregationOperation) {
        this.aggregation = aggregationOperation;
    }

}
