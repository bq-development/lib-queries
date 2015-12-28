package io.corbel.lib.queries.parser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.corbel.lib.queries.exception.InvalidParameterException;
import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.jaxrs.QueryParameters;
import io.corbel.lib.queries.parser.*;
import io.corbel.lib.queries.request.*;

/**
 * @author Francisco Sanchez
 */
public class QueryParametersParser {

    private final QueryParser queryParser;
    private final AggregationParser aggregationParser;
    private final SortParser sortParser;
    private final PaginationParser paginationParser;
    private final SearchParser searchParser;

    public QueryParametersParser(QueryParser queryParser, AggregationParser aggregationParser, SortParser sortParser,
            PaginationParser paginationParser, SearchParser searchParser) {
        this.queryParser = queryParser;
        this.aggregationParser = aggregationParser;
        this.sortParser = sortParser;
        this.paginationParser = paginationParser;
        this.searchParser = searchParser;
    }

    public QueryParameters createQueryParameters(int page, int pageSize, int maxPageSize, Optional<String> sort,
            Optional<List<String>> queries, Optional<List<String>> conditions, Optional<String> aggregation, Optional<String> search,
            boolean indexFieldsOnly) {
        return new QueryParameters(buildPagination(page, pageSize, maxPageSize), buildSort(sort), buildResourceQueries(queries),
                buildResourceQueries(conditions), buildAggregation(aggregation), buildSearch(search, indexFieldsOnly));
    }

    public QueryParameters createQueryParameters(int page, int pageSize, int maxPageSize, Optional<String> sort,
            Optional<List<String>> queries, Optional<List<String>> conditions, Optional<String> aggregation, Optional<String> search) {
        return createQueryParameters(page, pageSize, maxPageSize, sort, queries, conditions, aggregation, search, false);
    }

    private Pagination buildPagination(int page, int pageSize, int maxPageSize) {
        return paginationParser.parse(page, pageSize, maxPageSize);
    }

    private Optional<Sort> buildSort(Optional<String> sort) {
        try {
            return sort.isPresent() ? Optional.of(sortParser.parse(sort.get())) : Optional.empty();
        } catch (MalformedJsonQueryException | IllegalArgumentException e) {
            throw new InvalidParameterException(InvalidParameterException.Parameter.SORT, sort, e.getMessage(), e);
        }
    }

    private Optional<List<ResourceQuery>> buildResourceQueries(Optional<List<String>> optionalQueries) {
        return optionalQueries
                .map(queries -> queries.stream().map(stringQuery -> buildQuery(stringQuery, queryParser)).collect(Collectors.toList()));
    }

    private ResourceQuery buildQuery(String query, QueryParser queryParser) {
        try {
            return queryParser.parse(query);
        } catch (MalformedJsonQueryException e) {
            throw new InvalidParameterException(InvalidParameterException.Parameter.QUERY, query, e.getMessage(), e);
        }
    }

    private Optional<Aggregation> buildAggregation(Optional<String> aggregation) {
        if (aggregation.isPresent()) {
            try {
                return Optional.of(aggregationParser.parse(aggregation.get()));
            } catch (MalformedJsonQueryException e) {
                throw new InvalidParameterException(InvalidParameterException.Parameter.AGGREGATION, aggregation, e.getMessage(), e);
            }
        }
        return Optional.empty();
    }

    private Optional<Search> buildSearch(Optional<String> optionalSearch, boolean indexFieldsOnly) {
        if (optionalSearch.isPresent()) {
            try {
                return Optional.of(searchParser.parse(optionalSearch.get(), indexFieldsOnly));
            } catch (MalformedJsonQueryException e) {
                throw new InvalidParameterException(InvalidParameterException.Parameter.SEARCH, optionalSearch, e.getMessage(), e);
            }
        }
        return Optional.empty();
    }
}
