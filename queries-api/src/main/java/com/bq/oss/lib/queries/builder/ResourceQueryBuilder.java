package com.bq.oss.lib.queries.builder;

import java.util.Iterator;

import com.bq.oss.lib.queries.QueryNodeImpl;
import com.bq.oss.lib.queries.StringQueryLiteral;
import com.bq.oss.lib.queries.request.QueryOperator;
import com.bq.oss.lib.queries.request.ResourceQuery;
import com.bq.oss.lib.queries.request.QueryNode;

/**
 * @author Rubén Carrasco
 *
 */
public class ResourceQueryBuilder {

	private final ResourceQuery resourceQuery;

	public ResourceQueryBuilder(ResourceQuery resourceQuery) {
		if (resourceQuery == null) {
			this.resourceQuery = new ResourceQuery();
		} else {
			this.resourceQuery = resourceQuery;
		}
	}

	public ResourceQueryBuilder() {
		this(null);
	}

	public ResourceQueryBuilder add(String field, String value, QueryOperator operator) {
		StringQueryLiteral literal = new StringQueryLiteral();
		literal.setLiteral(value);
		QueryNodeImpl queryNode = new QueryNodeImpl(operator, field, literal);
		resourceQuery.addQueryNode(queryNode);
		return this;
	}

	public ResourceQueryBuilder add(String field, String value) {
		return add(field, value, QueryOperator.$EQ);
	}

	public ResourceQueryBuilder remove(String field) {
		Iterator<QueryNode> queryNodeIterator = resourceQuery.iterator();
		while (queryNodeIterator.hasNext()) {
			QueryNode queryNode = queryNodeIterator.next();
			if (queryNode.getField().matches(field)) {
				queryNodeIterator.remove();
			}
		}
		return this;
	}

	public ResourceQuery build() {
		return resourceQuery;
	}
}
