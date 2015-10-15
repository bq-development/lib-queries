package io.corbel.lib.queries.parser;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.ResourceQuery;

public interface QueryParser {

    public ResourceQuery parse(String queryString) throws MalformedJsonQueryException;

}
