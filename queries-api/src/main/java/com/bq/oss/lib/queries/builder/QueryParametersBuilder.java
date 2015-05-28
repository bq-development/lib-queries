package com.bq.oss.lib.queries.builder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bq.oss.lib.queries.exception.InvalidParameterException;
import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import com.bq.oss.lib.queries.jaxrs.QueryParameters;
import com.bq.oss.lib.queries.parser.AggregationParser;
import com.bq.oss.lib.queries.parser.PaginationParser;
import com.bq.oss.lib.queries.parser.QueryParser;
import com.bq.oss.lib.queries.parser.SortParser;
import com.bq.oss.lib.queries.request.*;

/**
 * @author Francisco Sanchez on 28/05/15.
 */
public class QueryParametersBuilder {

    private final QueryParser queryParser;
    private final AggregationParser aggregationParser;
    private final SortParser sortParser;
    private final PaginationParser paginationParser;

    public QueryParametersBuilder(QueryParser queryParser, AggregationParser aggregationParser, SortParser sortParser,
            PaginationParser paginationParser) {
        this.queryParser = queryParser;
        this.aggregationParser = aggregationParser;
        this.sortParser = sortParser;
        this.paginationParser = paginationParser;
    }

    public QueryParameters createQueryParameters(int page, int pageSize, int maxPageSize, Optional<String> sort,
            Optional<List<String>> queries, Optional<List<String>> conditions, Optional<String> aggregation, Optional<String> search) {
        return new QueryParameters(buildPagination(page, pageSize, maxPageSize), buildSort(sort), buildResourceQueries(queries),
                buildResourceQueries(conditions), buildAggregation(aggregation), buildSearch(search));
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
        return optionalQueries.map(queries -> {
            return queries.stream().map(stringQuery -> buildQuery(stringQuery, queryParser)).collect(Collectors.toList());
        });
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

    private Optional<ResourceSearch> buildSearch(Optional<String> optionalSearch) {
        return optionalSearch.map(search -> Optional.of(new ResourceSearch(search))).orElse(Optional.empty());
    }

}
