package io.corbel.lib.queries.parser;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.Search;

/**
 * @author Rubén Carrasco
 *
 */
public interface SearchParser {
    Search parse(String searchString) throws MalformedJsonQueryException;
}
