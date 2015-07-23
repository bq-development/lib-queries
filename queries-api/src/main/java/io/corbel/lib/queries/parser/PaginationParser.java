package io.corbel.lib.queries.parser;

import io.corbel.lib.queries.request.Pagination;

/**
 * @author Francisco Sanchez
 */
public interface PaginationParser {

    Pagination parse(int page, int pageSize, int maxPageSize);

}
