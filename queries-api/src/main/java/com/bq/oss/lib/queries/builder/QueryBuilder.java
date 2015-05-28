package com.bq.oss.lib.queries.builder;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.bq.oss.lib.queries.request.Pagination;
import com.bq.oss.lib.queries.request.ResourceQuery;
import com.bq.oss.lib.queries.request.Sort;

/**
 * @author by Alberto J. Rubio
 */
public interface QueryBuilder {

	QueryBuilder query(ResourceQuery resourceQuery);

	QueryBuilder query(List<ResourceQuery> resourceQueries);

	QueryBuilder pagination(Pagination pagination);

	QueryBuilder sort(Sort sort);

	Query build();
}
