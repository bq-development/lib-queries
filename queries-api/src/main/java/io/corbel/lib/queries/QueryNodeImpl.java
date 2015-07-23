package io.corbel.lib.queries;

import io.corbel.lib.queries.request.QueryNode;
import io.corbel.lib.queries.request.QueryLiteral;
import io.corbel.lib.queries.request.QueryOperator;

public class QueryNodeImpl implements QueryNode {

	private QueryOperator operator;
	private String field;
	private QueryLiteral<?> value;

	public QueryNodeImpl(QueryOperator operator, String field, QueryLiteral<?> value) {
		super();
		this.operator = operator;
		this.field = field;
		this.value = value;
	}

	@Override
	public QueryOperator getOperator() {
		return operator;
	}

	@Override
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public QueryLiteral<?> getValue() {
		return value;
	}

	public void setValue(QueryLiteral<?> value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		QueryNodeImpl other = (QueryNodeImpl) obj;
		if (field == null) {
			if (other.field != null) {
				return false;
			}
		} else if (!field.equals(other.field)) {
			return false;
		}
		if (operator != other.operator) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
}
