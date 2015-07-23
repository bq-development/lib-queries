package io.corbel.lib.queries.matcher;

import java.lang.reflect.InvocationTargetException;

import io.corbel.lib.queries.request.QueryNode;
import org.apache.commons.beanutils.PropertyUtils;

import io.corbel.lib.queries.exception.QueryMatchingException;
import io.corbel.lib.queries.request.QueryOperator;
import io.corbel.lib.queries.request.ResourceQuery;

public class DefaultQueryMatcher implements QueryMatcher {

	@Override
	public boolean matchObject(ResourceQuery resourceQuery, Object object) throws QueryMatchingException {
		for (QueryNode queryNode : resourceQuery) {
			if (queryNode.getOperator() == QueryOperator.$EXISTS) {
				try {
					Object objectProperty = getObjectProperty(object, queryNode.getField());
					if (objectProperty == null) {
						return false;
					}
				} catch (QueryMatchingException e) {
					return false;
				}
			} else {
				Object objectProperty = getObjectProperty(object, queryNode.getField());
				if (objectProperty == null) {
					return false;
				}

				if (!queryNode.getValue().operate(queryNode.getOperator(), objectProperty, this)) {
					return false;
				}
			}
		}
		return true;
	}

	private Object getObjectProperty(Object object, String field) throws QueryMatchingException {
		try {
			return PropertyUtils.getProperty(object, field);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new QueryMatchingException("Trying to access an invalid property.", e);
		}
	}
}
