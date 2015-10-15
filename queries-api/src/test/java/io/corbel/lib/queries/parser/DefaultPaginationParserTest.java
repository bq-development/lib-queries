package io.corbel.lib.queries.parser;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import io.corbel.lib.queries.exception.InvalidParameterException;
import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.Pagination;

/**
 * @author Francisco Sanchez
 *
 */
public class DefaultPaginationParserTest {

    private static final int TEST_PAGE_SIZE = 1;
    private static final int TEST_PAGE = 2;
    private static final int TEST_MAX_PAGE_SIZE = TEST_PAGE_SIZE + 1;
    private static final int TEST_PAGE_BIG_SIZE = TEST_MAX_PAGE_SIZE + 1;
    private static final int TEST_NEGATIVE_PAGE = -1;

    PaginationParser parser = new DefaultPaginationParser();

    @Test
    public void test() throws MalformedJsonQueryException {
        Pagination pagination = parser.parse(TEST_PAGE, TEST_PAGE_SIZE, TEST_MAX_PAGE_SIZE);
        assertThat(pagination.getPage()).isEqualTo(TEST_PAGE);
        assertThat(pagination.getPageSize()).isEqualTo(TEST_PAGE_SIZE);
    }

    @Test(expected = InvalidParameterException.class)
    public void testInvalidPageSize() throws MalformedJsonQueryException {
        parser.parse(TEST_PAGE, TEST_PAGE_BIG_SIZE, TEST_MAX_PAGE_SIZE);
    }

    @Test(expected = InvalidParameterException.class)
    public void testInvalidPage() throws MalformedJsonQueryException {
        parser.parse(TEST_NEGATIVE_PAGE, TEST_PAGE_SIZE, TEST_MAX_PAGE_SIZE);
    }

}
