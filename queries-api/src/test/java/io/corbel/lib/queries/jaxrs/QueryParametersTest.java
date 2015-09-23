package io.corbel.lib.queries.jaxrs;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;

import io.corbel.lib.queries.request.*;

import org.junit.Test;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;

/**
 * @author Francisco Sanchez
 */
public class QueryParametersTest {

    Pagination pagination;
    Optional<Sort> sort;
    Optional<List<ResourceQuery>> queries;
    Optional<List<ResourceQuery>> conditions;
    Optional<Aggregation> aggreagation;
    Optional<Search> search;


    @Test
    public void test() throws MalformedJsonQueryException {
        init();
        QueryParameters queryParameters = new QueryParameters(pagination, sort, queries, conditions, aggreagation, search);
        assertQueryParameters(queryParameters);

        init();
        queryParameters.setPagination(pagination);
        queryParameters.setSort(sort);
        queryParameters.setQueries(queries);
        queryParameters.setConditions(conditions);
        queryParameters.setAggregation(aggreagation);
        queryParameters.setSearch(search);
        assertQueryParameters(queryParameters);

        QueryParameters copyQuery = new QueryParameters(queryParameters);
        assertQueryParameters(copyQuery);
    }

    private void init() {
        pagination = mock(Pagination.class);
        sort = Optional.of(mock(Sort.class));
        queries = Optional.of(mock(List.class));
        conditions = Optional.of(mock(List.class));
        aggreagation = Optional.of(mock(Aggregation.class));
        search = Optional.of(mock(Search.class));
    }

    private void assertQueryParameters(QueryParameters queryParameters) {
        assertThat(queryParameters.getPagination()).isEqualTo(pagination);
        assertThat(queryParameters.getSort()).isEqualTo(sort);
        assertThat(queryParameters.getQueries()).isEqualTo(queries);
        assertThat(queryParameters.getConditions()).isEqualTo(conditions);
        assertThat(queryParameters.getAggregation()).isEqualTo(aggreagation);
        assertThat(queryParameters.getSearch()).isEqualTo(search);
    }


}
