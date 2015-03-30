package com.bq.oss.lib.queries.parser;

import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import com.bq.oss.lib.queries.request.ResourceQuery;

public interface QueryParser {

	public ResourceQuery parse(String queryString) throws MalformedJsonQueryException;

}
