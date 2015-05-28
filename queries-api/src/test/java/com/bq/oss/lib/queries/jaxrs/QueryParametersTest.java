package com.bq.oss.lib.queries.jaxrs;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.bq.oss.lib.queries.exception.InvalidParameterException;
import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import com.bq.oss.lib.queries.exception.QueryMatchingException;
import com.bq.oss.lib.queries.parser.AggregationParser;
import com.bq.oss.lib.queries.parser.QueryParser;
import com.bq.oss.lib.queries.parser.SortParser;
import com.bq.oss.lib.queries.request.Aggregation;
import com.bq.oss.lib.queries.request.ResourceQuery;
import com.bq.oss.lib.queries.request.ResourceSearch;
import com.bq.oss.lib.queries.request.Sort;

/**
 * Created by Francisco Sanchez on 12/05/15.
 */
public class QueryParametersTest {
    private static final int TEST_PAGE_SIZE = 1;
    private static final int TEST_PAGE = 2;
    private static final int TEST_MAX_PAGE_SIZE = TEST_PAGE_SIZE + 1;
    private static final int TEST_PAGE_BIG_SIZE = TEST_MAX_PAGE_SIZE + 1;
    private static final Optional<String> TEST_SORT = Optional.of("ASC");
    private static final Sort TEST_SORT_PARSED = new Sort(TEST_SORT.get(), "Field");
    private static final Optional<String> TEST_SORT_EMPTY = Optional.empty();

    private static final Optional<List<String>> TEST_OPTIONAL_QUERY = Optional.of(Arrays.asList("test"));
    private static final ResourceQuery TEST_OPTIONAL_QUERY_PARSED = new ResourceQuery();


    private static final String QUERY1 = "test1";
    private static final String QUERY2 = "test2";
    private static final Optional<List<String>> TEST_OPTIONAL_LIST_QUERIES = Optional.of(Arrays.asList(QUERY1, QUERY2));
    private static final ResourceQuery QUERY1_PARSED = new ResourceQuery();
    private static final ResourceQuery QUERY2_PARSED = new ResourceQuery();

    private static final Optional<List<String>> TEST_OPTIONAL_LIST_QUERIES_EMPTY = Optional.empty();

    private static final Optional<String> TEST_AGGREGATION = Optional.of("testAgg");
    private static final Aggregation TEST_AGGREGATION_PARSED = mock(Aggregation.class);

    private static final Optional<String> TEST_AGGREGATION_EMPTY = Optional.empty();

    private static final Optional<String> TEST_SEARCH = Optional.of("test search");
    private static final ResourceSearch TEST_SEARCH_PARSED = new ResourceSearch(TEST_SEARCH.get());

    private static final Optional<String> TEST_SEARCH_EMPTY = Optional.empty();


    private QueryParser queryParserMock;
    private AggregationParser aggregationParserMock;
    private SortParser sortParserMock;

    @Before
    public void before() {
        queryParserMock = mock(QueryParser.class);
        aggregationParserMock = mock(AggregationParser.class);
        sortParserMock = mock(SortParser.class);
    }

    @Test
    public void queryParametersListQueriesTest() throws QueryMatchingException, MalformedJsonQueryException {
        when(sortParserMock.parse(TEST_SORT.get())).thenReturn(TEST_SORT_PARSED);
        when(queryParserMock.parse(QUERY1)).thenReturn(QUERY1_PARSED);
        when(queryParserMock.parse(QUERY2)).thenReturn(QUERY2_PARSED);
        when(aggregationParserMock.parse(TEST_AGGREGATION.get())).thenReturn(TEST_AGGREGATION_PARSED);

        QueryParameters queryParameters = new QueryParameters(TEST_PAGE_SIZE, TEST_PAGE, TEST_MAX_PAGE_SIZE, TEST_SORT,
                TEST_OPTIONAL_LIST_QUERIES, queryParserMock, TEST_AGGREGATION, aggregationParserMock, sortParserMock, TEST_SEARCH);

        assertThat(queryParameters.getPagination().getPageSize()).isEqualTo(TEST_PAGE_SIZE);
        assertThat(queryParameters.getPagination().getPage()).isEqualTo(TEST_PAGE);

        assertThat(queryParameters.getQueries().get()).isEqualTo(Arrays.asList(QUERY1_PARSED, QUERY2_PARSED));

        assertThat(queryParameters.getAggregation().get()).isEqualTo(TEST_AGGREGATION_PARSED);
        assertThat(queryParameters.getSort().get()).isEqualTo(TEST_SORT_PARSED);

        assertThat(queryParameters.getSearch().get()).isEqualTo(TEST_SEARCH_PARSED);
    }

    @Test
    public void queryParametersEmptyTest() throws QueryMatchingException, MalformedJsonQueryException {

        QueryParameters queryParameters = new QueryParameters(TEST_PAGE_SIZE, TEST_PAGE, TEST_MAX_PAGE_SIZE, TEST_SORT_EMPTY,
                TEST_OPTIONAL_LIST_QUERIES_EMPTY, queryParserMock, TEST_AGGREGATION_EMPTY, aggregationParserMock, sortParserMock,
                TEST_SEARCH_EMPTY);

        assertThat(queryParameters.getPagination().getPageSize()).isEqualTo(TEST_PAGE_SIZE);
        assertThat(queryParameters.getPagination().getPage()).isEqualTo(TEST_PAGE);

        assertThat(queryParameters.getQueries()).isEqualTo(Optional.empty());

        assertThat(queryParameters.getAggregation()).isEqualTo(Optional.empty());
        assertThat(queryParameters.getSort()).isEqualTo(Optional.empty());

        assertThat(queryParameters.getSearch()).isEqualTo(Optional.empty());

    }

    @Test(expected = InvalidParameterException.class)
    public void queryParametersBadQueryTest() throws QueryMatchingException, MalformedJsonQueryException {
        when(queryParserMock.parse(any())).thenThrow(MalformedJsonQueryException.class);

        QueryParameters queryParameters = new QueryParameters(TEST_PAGE_SIZE, TEST_PAGE, TEST_MAX_PAGE_SIZE, TEST_SORT_EMPTY,
                TEST_OPTIONAL_LIST_QUERIES, queryParserMock, TEST_AGGREGATION_EMPTY, aggregationParserMock, sortParserMock,
                TEST_SEARCH_EMPTY);
    }

    @Test(expected = InvalidParameterException.class)
    public void queryParametersBadSortTest() throws QueryMatchingException, MalformedJsonQueryException {
        when(sortParserMock.parse(any())).thenThrow(MalformedJsonQueryException.class);

        QueryParameters queryParameters = new QueryParameters(TEST_PAGE_SIZE, TEST_PAGE, TEST_MAX_PAGE_SIZE, TEST_SORT,
                TEST_OPTIONAL_LIST_QUERIES_EMPTY, queryParserMock, TEST_AGGREGATION_EMPTY, aggregationParserMock, sortParserMock,
                TEST_SEARCH_EMPTY);
    }

    @Test(expected = InvalidParameterException.class)
    public void queryParametersBadAggregationTest() throws QueryMatchingException, MalformedJsonQueryException {
        when(aggregationParserMock.parse(any())).thenThrow(MalformedJsonQueryException.class);

        QueryParameters queryParameters = new QueryParameters(TEST_PAGE_SIZE, TEST_PAGE, TEST_MAX_PAGE_SIZE, TEST_SORT_EMPTY,
                TEST_OPTIONAL_LIST_QUERIES_EMPTY, queryParserMock, TEST_AGGREGATION, aggregationParserMock, sortParserMock,
                TEST_SEARCH_EMPTY);
    }

    @Test(expected = InvalidParameterException.class)
    public void queryParametersPageSizeBiggerThanMaxTest() throws QueryMatchingException, MalformedJsonQueryException {
        QueryParameters queryParameters = new QueryParameters(TEST_PAGE_BIG_SIZE, TEST_PAGE, TEST_MAX_PAGE_SIZE, TEST_SORT_EMPTY,
                TEST_OPTIONAL_LIST_QUERIES_EMPTY, queryParserMock, TEST_AGGREGATION_EMPTY, aggregationParserMock, sortParserMock,
                TEST_SEARCH_EMPTY);
    }


    @Test
    public void queryParametersTest() throws QueryMatchingException, MalformedJsonQueryException {
        when(sortParserMock.parse(TEST_SORT.get())).thenReturn(TEST_SORT_PARSED);
        when(queryParserMock.parse(TEST_OPTIONAL_QUERY.get().get(0))).thenReturn(TEST_OPTIONAL_QUERY_PARSED);
        when(aggregationParserMock.parse(TEST_AGGREGATION.get())).thenReturn(TEST_AGGREGATION_PARSED);

        QueryParameters queryParameters = new QueryParameters(TEST_PAGE_SIZE, TEST_PAGE, TEST_MAX_PAGE_SIZE, TEST_SORT,
                TEST_OPTIONAL_QUERY, queryParserMock, TEST_AGGREGATION, aggregationParserMock, sortParserMock, TEST_SEARCH);

        assertThat(queryParameters.getPagination().getPageSize()).isEqualTo(TEST_PAGE_SIZE);
        assertThat(queryParameters.getPagination().getPage()).isEqualTo(TEST_PAGE);

        assertThat(queryParameters.getQueries().get()).isEqualTo(Arrays.asList(TEST_OPTIONAL_QUERY_PARSED));

        assertThat(queryParameters.getAggregation().get()).isEqualTo(TEST_AGGREGATION_PARSED);
        assertThat(queryParameters.getSort().get()).isEqualTo(TEST_SORT_PARSED);

        assertThat(queryParameters.getSearch().get()).isEqualTo(TEST_SEARCH_PARSED);
    }

    @Test
    public void queryParametersSetQueryTest() throws QueryMatchingException, MalformedJsonQueryException {
        QueryParameters queryParameters = new QueryParameters(TEST_PAGE_SIZE, TEST_PAGE, TEST_MAX_PAGE_SIZE, TEST_SORT_EMPTY,
                TEST_OPTIONAL_LIST_QUERIES_EMPTY, queryParserMock, TEST_AGGREGATION_EMPTY, aggregationParserMock, sortParserMock,
                TEST_SEARCH_EMPTY);

        queryParameters.setQuery(Optional.of(TEST_OPTIONAL_QUERY_PARSED));
        assertThat(queryParameters.getQueries().get().get(0)).isEqualTo(TEST_OPTIONAL_QUERY_PARSED);
    }

}
