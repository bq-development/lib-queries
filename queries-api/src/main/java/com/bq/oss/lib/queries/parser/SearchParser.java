package com.bq.oss.lib.queries.parser;

import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import com.bq.oss.lib.queries.request.Search;

/**
 * @author Rubén Carrasco
 *
 */
public interface SearchParser {
    Search parse(String searchString) throws MalformedJsonQueryException;
}
