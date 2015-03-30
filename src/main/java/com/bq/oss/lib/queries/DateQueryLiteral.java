package com.bq.oss.lib.queries;

import java.util.Date;

import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import com.bq.oss.lib.queries.exception.QueryMatchingException;
import com.bq.oss.lib.queries.request.QueryLiteral;

public class DateQueryLiteral extends QueryLiteral<Date> {

	public DateQueryLiteral() {
		super();
	}

	public DateQueryLiteral(Date date) {
		super(date);
	}

	@Override
	protected boolean eq(Object object) throws QueryMatchingException {
		return ((Date) object).equals(literal);
	}

	@Override
	protected boolean ne(Object object) throws QueryMatchingException {
		return !((Date) object).equals(literal);
	}

	@Override
	protected boolean gt(Object object) throws QueryMatchingException {
		return ((Date) object).after(literal);
	}

	@Override
	protected boolean gte(Object object) throws QueryMatchingException {
		Date extractDate = ((Date) object);
		return extractDate.equals(literal) || extractDate.after(literal);
	}

	@Override
	protected boolean lt(Object object) throws QueryMatchingException {
		return ((Date) object).before(literal);
	}

	@Override
	protected boolean lte(Object object) throws QueryMatchingException {
		Date extractDate = ((Date) object);
		return extractDate.equals(literal) || extractDate.before(literal);
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
		DateQueryLiteral other = (DateQueryLiteral) obj;
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
		return "\"ISODate(" + ISODateTimeFormat.dateTimeNoMillis().withZone(DateTimeZone.UTC).print(literal.getTime())
				+ ")\"";
	}
}
