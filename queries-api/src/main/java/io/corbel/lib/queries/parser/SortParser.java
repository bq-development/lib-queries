package io.corbel.lib.queries.parser;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.Sort;

/**
 * @author Francisco Sanchez
 */
public interface SortParser {
    Sort parse(String queryString) throws MalformedJsonQueryException;
}
