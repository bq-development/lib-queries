/*
 * Copyright (C) 2014 StarTIC
 */
package com.bq.oss.lib.queries.jaxrs;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bq.oss.lib.queries.exception.InvalidParameterException;
import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import com.bq.oss.lib.queries.parser.AggregationParser;
import com.bq.oss.lib.queries.parser.QueryParser;
import com.bq.oss.lib.queries.parser.SortParser;
import com.bq.oss.lib.queries.request.*;


/**
 * @author Alexander De Leon
 */
public class QueryParameters {
    private Pagination pagination;
    private Optional<Sort> sort;
    private Optional<List<ResourceQuery>> listQueries;
    private Optional<ResourceSearch> search;
    private Optional<Aggregation> aggregationOperation;

    public QueryParameters(int pageSize, int page, int maxPageSize, Optional<String> sort, Optional<?> optionalQueries,
                           QueryParser queryParser, Optional<String> aggregation, AggregationParser aggregationParser, SortParser sortParser,
                           Optional<String> search) {
        this.pagination = buildPagination(page, pageSize, maxPageSize);
        this.sort = buildSort(sort, sortParser);
        listQueries = buildQueries(optionalQueries, queryParser);
        this.aggregationOperation = buildOptionalAggregation(aggregation, aggregationParser);
        this.search = buildSearch(search);
    }

    public QueryParameters(QueryParameters other) {
        this.pagination = other.pagination;
        this.sort = other.sort;
        this.listQueries = other.listQueries;
        this.aggregationOperation = other.aggregationOperation;
        this.search = other.search;
    }

    private Optional<ResourceSearch> buildSearch(Optional<String> optionalSearch) {
        return optionalSearch.map(search -> Optional.of(new ResourceSearch(search))).orElse(Optional.empty());
    }

    public Pagination getPagination() {
        return pagination;
    }

    @Deprecated
    public Optional<ResourceQuery> getQuery() {
        return Optional.ofNullable(listQueries.map(queries -> queries.get(0)).orElse(null));
    }

    public Optional<List<ResourceQuery>> getQueries() {
        return listQueries;
    }

    public Optional<Sort> getSort() {
        return sort;
    }

    public Optional<Aggregation> getAggregation() {
        return aggregationOperation;
    }

    public Optional<ResourceSearch> getSearch() {
        return search;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Deprecated
    public void setQuery(Optional<ResourceQuery> optionalQuery) {
        listQueries = Optional.ofNullable(optionalQuery.map(query -> Arrays.asList(query)).orElse(null));
    }

    public void setQueries(Optional<List<ResourceQuery>> listQueries) {
        this.listQueries = listQueries;
    }

    public void setSearch(Optional<ResourceSearch> search) {
        this.search = search;
    }

    public void setSort(Optional<Sort> sort) {
        this.sort = sort;
    }

    public void setAggregation(Optional<Aggregation> aggregationOperation) {
        this.aggregationOperation = aggregationOperation;
    }

    private Optional<Aggregation> buildOptionalAggregation(Optional<String> aggregation,
                                                           AggregationParser aggregationParser) {

        if (aggregation.isPresent()) {
            try {
                return Optional.of(aggregationParser.parse(aggregation.get()));
            } catch (MalformedJsonQueryException e) {
                throw new InvalidParameterException(InvalidParameterException.Parameter.AGGREGATION, aggregation
                        , e.getMessage(), e);
            }
        }
        return Optional.empty();
    }

    private Optional<Sort> buildSort(Optional<String> sort, SortParser sortParser) {
        try {
            return sort.isPresent() ? Optional.of(sortParser.parse(sort.get())) : Optional.empty();
        } catch (MalformedJsonQueryException | IllegalArgumentException e) {
            throw new InvalidParameterException(InvalidParameterException.Parameter.SORT, sort, e.getMessage(), e);
        }
    }

    private Pagination buildPagination(int page, int pageSize, int macPageSize) {
        return new Pagination(assertValidPage(page), assertValidPageSize(pageSize, macPageSize));
    }

    private Optional<List<ResourceQuery>> buildQueries(Optional<?> optionalQueries, QueryParser queryParser) {
        return optionalQueries.map(queries -> {
            //DEPRECATED: Quries as Optional<String>
            if (queries instanceof String) {
                return Arrays.asList(buildQuery((String) queries, queryParser));
            } else {
                return ((List<String>) queries).stream().map(stringQuery -> buildQuery(stringQuery, queryParser)).collect(Collectors.toList());
            }
        });
    }

    private ResourceQuery buildQuery(String query, QueryParser queryParser) {
        try {
            return queryParser.parse(query);
        } catch (MalformedJsonQueryException e) {
            throw new InvalidParameterException(InvalidParameterException.Parameter.QUERY, query
                    , e.getMessage(), e);
        }
    }

    private int assertValidPageSize(int pageSize, int maxPageSize) {
        if (!(pageSize > 0 && pageSize <= maxPageSize)) {
            throw new InvalidParameterException(InvalidParameterException.Parameter.PAGE_SIZE, pageSize
                    , "Invalid pageSize: " + pageSize);
        }
        return pageSize;
    }

    private int assertValidPage(int page) {
        if (page < 0) {
            throw new InvalidParameterException(InvalidParameterException.Parameter.PAGE, page
                    , "Invalid page: " + page);
        }
        return page;
    }

}
