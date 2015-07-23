package io.corbel.lib.queries.builder;

import java.util.List;

import io.corbel.lib.queries.request.Pagination;
import io.corbel.lib.queries.request.ResourceQuery;
import io.corbel.lib.queries.request.Sort;
import org.springframework.data.mongodb.core.query.Query;

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
