package com.bq.oss.lib.queries;

import java.util.List;

import com.bq.oss.lib.queries.matcher.QueryMatcher;
import com.bq.oss.lib.queries.exception.QueryMatchingException;
import com.bq.oss.lib.queries.request.QueryLiteral;
import com.bq.oss.lib.queries.request.ResourceQuery;

/**
 * @author Rubén Carrasco
 *
 */
public class ResourceQueryQueryLiteral extends QueryLiteral<ResourceQuery> {

	@Override
	protected boolean elemMatch(Object object, QueryMatcher queryMatcher)
			throws QueryMatchingException {
		List objectList = (List) object;
		for (Object object2 : objectList) {
			if (queryMatcher.matchObject(literal, object2)) {
				return true;
			}
		}
		return false;
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
		ResourceQueryQueryLiteral other = (ResourceQueryQueryLiteral) obj;
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
		return getLiteral().toString();
	}
}
