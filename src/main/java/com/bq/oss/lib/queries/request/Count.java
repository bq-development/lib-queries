package com.bq.oss.lib.queries.request;

import com.bq.oss.lib.queries.BooleanQueryLiteral;
import com.bq.oss.lib.queries.QueryNodeImpl;

/**
 * @author Rubén Carrasco
 *
 */
public class Count extends FieldAggregation {

	private static final String ALL = "*";

	public Count(String field) {
		super(field);
	}

	@Override
	public ResourceQuery operate(ResourceQuery resourceQuery) {
		resourceQuery = super.operate(resourceQuery);
		if (!field.equals(ALL)) {
			BooleanQueryLiteral literal = new BooleanQueryLiteral();
			literal.setLiteral(true);
			QueryNodeImpl queryNode = new QueryNodeImpl(QueryOperator.$EXISTS, field, literal);
			resourceQuery.addQueryNode(queryNode);
		}
		return resourceQuery;
	};

	@Override
	public AggregationOperator getOperator() {
		return AggregationOperator.$COUNT;
	}
}
