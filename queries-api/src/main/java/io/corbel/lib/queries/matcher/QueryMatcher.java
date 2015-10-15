package io.corbel.lib.queries.matcher;

import io.corbel.lib.queries.exception.QueryMatchingException;
import io.corbel.lib.queries.request.ResourceQuery;

public interface QueryMatcher {

    boolean matchObject(ResourceQuery resourceQuery, Object object) throws QueryMatchingException;
}
