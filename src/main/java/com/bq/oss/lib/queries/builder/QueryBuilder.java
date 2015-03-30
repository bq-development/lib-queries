package com.bq.oss.lib.queries.builder;

import org.springframework.data.mongodb.core.query.Query;

import com.bq.oss.lib.queries.request.Pagination;
import com.bq.oss.lib.queries.request.ResourceQuery;
import com.bq.oss.lib.queries.request.Sort;

/**
 * Created by Alberto J. Rubio
 */
public interface QueryBuilder {

	QueryBuilder query(ResourceQuery resourceQuery);

	QueryBuilder pagination(Pagination pagination);

	QueryBuilder sort(Sort sort);

	Query build();
}
