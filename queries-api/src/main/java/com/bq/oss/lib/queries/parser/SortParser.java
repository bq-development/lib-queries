package com.bq.oss.lib.queries.parser;

import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import com.bq.oss.lib.queries.request.Sort;

/**
 * Created by Francisco Sanchez on 12/05/15.
 */
public interface SortParser {
    Sort parse(String queryString) throws MalformedJsonQueryException;
}
