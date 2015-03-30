package com.bq.oss.lib.queries.request;

public interface QueryNode {
	QueryOperator getOperator();

	String getField();

	QueryLiteral<?> getValue();
}
