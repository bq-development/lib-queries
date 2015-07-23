package io.corbel.lib.queries;

import java.math.BigDecimal;

import io.corbel.lib.queries.exception.QueryMatchingException;
import io.corbel.lib.queries.request.QueryLiteral;

public class NumericQueryLiteral<T> extends QueryLiteral<T> {
	public NumericQueryLiteral() {
		super();
	}

	public NumericQueryLiteral(T literal) {
		super(literal);
	}

	@Override
	protected boolean eq(Object object) throws QueryMatchingException {
		return compare(object) == 0;
	};

	@Override
	protected boolean ne(Object object) throws QueryMatchingException {
		return compare(object) != 0;
	};

	@Override
	protected boolean gt(Object object) throws QueryMatchingException {
		return compare(object) == 1;
	}

	@Override
	protected boolean gte(Object object) throws QueryMatchingException {
		int result = compare(object);
		return result == 1 || result == 0;
	};

	@Override
	protected boolean lt(Object object) throws QueryMatchingException {
		return compare(object) == -1;
	};

	@Override
	protected boolean lte(Object object) throws QueryMatchingException {
		int result = compare(object);
		return result == -1 || result == 0;
	};

	private int compare(Object object) {
		return new BigDecimal(object.toString()).compareTo(new BigDecimal(literal.toString()));
	};
}
