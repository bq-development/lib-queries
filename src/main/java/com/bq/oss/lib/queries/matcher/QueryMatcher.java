package com.bq.oss.lib.queries.matcher;

import com.bq.oss.lib.queries.exception.QueryMatchingException;
import com.bq.oss.lib.queries.request.ResourceQuery;

public interface QueryMatcher {

	boolean matchObject(ResourceQuery resourceQuery, Object object) throws QueryMatchingException;
}
