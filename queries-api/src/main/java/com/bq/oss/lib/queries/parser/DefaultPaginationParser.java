package com.bq.oss.lib.queries.parser;

import com.bq.oss.lib.queries.exception.InvalidParameterException;
import com.bq.oss.lib.queries.request.Pagination;

/**
 * @author Francisco Sanchez on 28/05/15.
 */
public class DefaultPaginationParser implements PaginationParser {

    @Override
    public Pagination parse(int page, int pageSize, int maxPageSize) {
        return new Pagination(assertValidPage(page), assertValidPageSize(pageSize, maxPageSize));
    }

    private int assertValidPageSize(int pageSize, int maxPageSize) {
        if (!(pageSize > 0 && pageSize <= maxPageSize)) {
            throw new InvalidParameterException(InvalidParameterException.Parameter.PAGE_SIZE, pageSize, "Invalid pageSize: " + pageSize);
        }
        return pageSize;
    }

    private int assertValidPage(int page) {
        if (page < 0) {
            throw new InvalidParameterException(InvalidParameterException.Parameter.PAGE, page, "Invalid page: " + page);
        }
        return page;
    }


}
