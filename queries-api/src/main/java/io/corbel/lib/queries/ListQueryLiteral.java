package io.corbel.lib.queries;

import io.corbel.lib.queries.exception.QueryMatchingException;
import io.corbel.lib.queries.request.QueryLiteral;
import io.corbel.lib.queries.request.QueryOperator;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ListQueryLiteral extends QueryLiteral<List<QueryLiteral>> {

	public ListQueryLiteral() {
		super();
	}

	public ListQueryLiteral(List list) {
		super(list);
	}

	@Override
	protected boolean in(Object object) throws QueryMatchingException {
		for (QueryLiteral queryLiteral : literal) {
			if (queryLiteral.operate(QueryOperator.$EQ, object)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean all(Object object) throws QueryMatchingException {
		List objectList = (List) object;
		for (QueryLiteral queryLiteral : literal) {
			Iterator iterator = objectList.iterator();
			boolean flag = false;
			while (iterator.hasNext() && !flag) {
				flag = queryLiteral.operate(QueryOperator.$EQ, iterator.next());
			}
			if (!flag) {
				return false;
			}
		}
		return true;
	}

    @Override
	protected boolean eq(Object object) throws QueryMatchingException {
		List objectList = (List) object;
		return objectList.size() == literal.size() && all(object);
	}

	@Override
	protected boolean ne(Object object) throws QueryMatchingException {
		List objectList = (List) object;
		return objectList.size() != literal.size() || !all(object);
	}

	public List<Object> getLiterals() {
		return literal.stream().map(QueryLiteral::getLiteral).collect(Collectors.toList());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((literal == null) ? 0 : literal.hashCode());
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
		ListQueryLiteral other = (ListQueryLiteral) obj;
		if (literal == null) {
			if (other.literal != null) {
				return false;
			}
		} else if (!literal.equals(other.literal)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return literal.stream().map(item -> item.getLiteral().toString()).collect(Collectors.joining(",", "[", "]"));
	}
}
