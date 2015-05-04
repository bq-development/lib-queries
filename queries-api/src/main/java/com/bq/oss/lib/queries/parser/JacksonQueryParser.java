package com.bq.oss.lib.queries.parser;

import java.time.Duration;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bq.oss.lib.queries.*;
import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import com.bq.oss.lib.queries.request.QueryOperator;
import com.bq.oss.lib.queries.request.ResourceQuery;
import com.bq.oss.lib.queries.request.QueryNode;
import org.joda.time.format.ISODateTimeFormat;

import com.bq.oss.lib.queries.request.QueryLiteral;
import com.fasterxml.jackson.databind.JsonNode;

public class JacksonQueryParser implements QueryParser {

	private static final String OPERATOR_START = "$";
	private static final Pattern ISO_DATE_PATTERN = Pattern.compile("^ISODate[(](.*?)[)]$");
	private static final Pattern PERIOD_PATTERN = Pattern.compile("^Period[(](.*?)[)]$");

	private final CustomJsonParser jsonParser;

	public JacksonQueryParser(CustomJsonParser jsonParser) {
		this.jsonParser = jsonParser;
	}

	@Override
	public ResourceQuery parse(String queryString) throws MalformedJsonQueryException {
		JsonNode nodes = jsonParser.readValueAsTree(queryString);
		return getParseQueriesFromTree(nodes);
	}

	private ResourceQuery getParseQueriesFromTree(JsonNode nodes) throws MalformedJsonQueryException {
		ResourceQuery resourceQuery = new ResourceQuery();
		for (JsonNode node : nodes) {
			if (node.isObject()) {
				resourceQuery.addQueryNode(getQueryNodeFromJsonNode(node));
			} else {
				throw new MalformedJsonQueryException("Unexpected array");
			}
		}
		return resourceQuery;
	}

	private QueryNode getQueryNodeFromJsonNode(JsonNode node) throws MalformedJsonQueryException {
		Iterator<String> fieldNames = node.fieldNames();
		if (fieldNames.hasNext()) {
			String key = fieldNames.next();
			if (fieldNames.hasNext()) {
				throw new MalformedJsonQueryException("Wrong number of fields (Expected one)");
			}
			if (key.startsWith(OPERATOR_START)) {
				return getQueryNode(getOperator(key), node.get(key));
			} else {
				return getQueryNode(QueryOperator.$EQ, node);
			}
		} else {
			throw new MalformedJsonQueryException("Empty object");
		}
	}

	private QueryOperator getOperator(String key) throws MalformedJsonQueryException {
		try {
			return QueryOperator.valueOf(key.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new MalformedJsonQueryException("Unknown operator " + key);
		}
	}

	private QueryNodeImpl getQueryNode(QueryOperator operator, JsonNode nodeField) throws MalformedJsonQueryException {
		if (nodeField.fieldNames().hasNext()) {
			String field = nodeField.fieldNames().next();
			QueryLiteral<?> value = generateQueryLiteral(nodeField.get(field), operator);
			return new QueryNodeImpl(operator, field, value);
		} else {
			throw new MalformedJsonQueryException("Empty filter");
		}
	}

	private QueryLiteral<?> generateQueryLiteral(JsonNode nodeField, QueryOperator operator)
			throws MalformedJsonQueryException {

		if (nodeField.isObject()) {
			throw new MalformedJsonQueryException("Unsupported operation. No operator supports Object type");
		} else if (nodeField.isArray()) {
			if (!operator.isArrayOperator()) {
				throw new MalformedJsonQueryException(
						"Unsupported operation. Only $ALL, $IN and $ELEM_MATCH support operations with non primitive types.");
			}
			if (operator == QueryOperator.$ELEM_MATCH) {
				return generateResourceQueryQueryLiteral(nodeField);
			} else {
				return generateArrayQueryLiteral(nodeField);
			}
		} else if (operator.isArrayOperator()) {
			throw new MalformedJsonQueryException(
					"Unsopported operation. Only $EQ, $GT, $GTE, $LT, $LTE, $NE and $LIKE support operations with primitives.");
		}
		return generatePrimitiveQueryLiteral(nodeField);
	}

	private QueryLiteral<?> generateResourceQueryQueryLiteral(JsonNode nodeField) throws MalformedJsonQueryException {
		QueryLiteral<ResourceQuery> literal = new ResourceQueryQueryLiteral();
		literal.setLiteral(getParseQueriesFromTree(nodeField));
		return literal;
	}

	@SuppressWarnings("rawtypes")
	private QueryLiteral<?> generateArrayQueryLiteral(JsonNode nodeField) throws MalformedJsonQueryException {
		List<QueryLiteral> list = new ArrayList<>();
        for (JsonNode aNodeField : nodeField) {
            list.add(generatePrimitiveQueryLiteral(aNodeField));
        }
		QueryLiteral<List<QueryLiteral>> literal = new ListQueryLiteral();
		literal.setLiteral(list);
		return literal;
	}

	@SuppressWarnings("unchecked")
	private QueryLiteral<?> generatePrimitiveQueryLiteral(JsonNode nodeField) throws MalformedJsonQueryException {

        QueryLiteral<?> literal;

		if (nodeField.isDouble()) {
			literal = new DoubleQueryLiteral();
			((QueryLiteral<Double>) literal).setLiteral(nodeField.asDouble());
		} else if (nodeField.canConvertToLong()) {
			literal = new LongQueryLiteral();
			((QueryLiteral<Long>) literal).setLiteral(nodeField.asLong());
		} else if (nodeField.isBoolean()) {
			literal = new BooleanQueryLiteral();
			((QueryLiteral<Boolean>) literal).setLiteral(nodeField.asBoolean());
		} else if (nodeField.isTextual()) {
			String valueString = nodeField.textValue();
			Matcher isoDateMatcher = ISO_DATE_PATTERN.matcher(valueString);
			if (isoDateMatcher.find()) {
				try {
					literal = new DateQueryLiteral();
					((QueryLiteral<Date>) literal).setLiteral(ISODateTimeFormat.dateTimeNoMillis()
							.parseDateTime(isoDateMatcher.group(1)).toDate());
				} catch (IllegalArgumentException e) {
					throw new MalformedJsonQueryException("Wrong date format", e);
				}
			} else {
				literal = new StringQueryLiteral();
				Matcher isoPeriodMatcher = PERIOD_PATTERN.matcher(valueString);
				if (isoPeriodMatcher.find()) {
                    TemporalAmount temporalAmount;
                    try {
                        temporalAmount = Period.parse(isoPeriodMatcher.group(1));
                    } catch (DateTimeParseException ignored) {
                        temporalAmount = Duration.parse(isoPeriodMatcher.group(1));
                    }
                    ((QueryLiteral<String>) literal).setLiteral(temporalAmount.toString());
				} else {
                    ((QueryLiteral<String>) literal).setLiteral(valueString);
				}
			}
		} else {
			throw new MalformedJsonQueryException("Unsupported type");
		}
		return literal;
	}
}
