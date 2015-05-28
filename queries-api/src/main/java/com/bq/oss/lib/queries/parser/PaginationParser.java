package com.bq.oss.lib.queries.parser;

import com.bq.oss.lib.queries.request.Pagination;

/**
 * @author Francisco Sanchez on 28/05/15.
 */
public interface PaginationParser {

    Pagination parse(int page, int pageSize, int maxPageSize);

}
