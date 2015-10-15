package io.corbel.lib.queries.mongo.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Criteria;

import io.corbel.lib.mongo.SafeKeys;
import io.corbel.lib.queries.ListQueryLiteral;
import io.corbel.lib.queries.request.QueryLiteral;
import io.corbel.lib.queries.request.QueryNode;
import io.corbel.lib.queries.request.QueryOperator;
import io.corbel.lib.queries.request.ResourceQuery;

/**
 * @author Rubén Carrasco
 *
 */
public class CriteriaBuilder {

    private static Function<QueryNode, QueryNode> IDENTITY = t -> t;

    public static Criteria buildFromResourceQueries(List<ResourceQuery> resourceQueries) {
        return buildFromResourceQueries(resourceQueries, IDENTITY);
    }

    public static Criteria buildFromResourceQueries(List<ResourceQuery> resourceQueries,
            Function<QueryNode, QueryNode> queryNodeTransformer) {
        List<Criteria> criterias = new ArrayList<>();
        for (ResourceQuery resourceQuery : resourceQueries) {
            criterias.add(buildFromResourceQuery(resourceQuery, queryNodeTransformer));
        }

        if (criterias.size() == 0) {
            return new Criteria();
        } else if (criterias.size() == 1) {
            return criterias.get(0);
        } else {
            return new Criteria().orOperator(criterias.toArray(new Criteria[criterias.size()]));
        }
    }

    public static Criteria buildFromResourceQuery(ResourceQuery resourceQuery) {
        return buildFromResourceQuery(resourceQuery, IDENTITY);
    }

    public static Criteria buildFromResourceQuery(ResourceQuery resourceQuery, Function<QueryNode, QueryNode> queryNodeTransformer) {
        List<Criteria> criterias = new ArrayList<Criteria>();

        for (QueryNode queryNode : resourceQuery) {
            String safeQuery = Arrays.stream(queryNode.getField().split("[.]")).map(SafeKeys::getSafeKey).collect(Collectors.joining("."));

            criterias.add(criteria(queryNode.getOperator(), safeQuery, (queryNodeTransformer.apply(queryNode)).getValue()));
        }

        switch (criterias.size()) {
            case 0:
                return new Criteria();
            case 1:
                return criterias.get(0);
            default:
                return new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        }
    }

    private static Criteria criteria(QueryOperator operator, String field, QueryLiteral<?> value) {
        Criteria criteria = new Criteria(field);
        switch (operator) {
            case $ALL:
                return criteria.all(((ListQueryLiteral) value).getLiterals());
            case $EQ:
                return criteria.is(value.getLiteral());
            case $GT:
                return criteria.gt(value.getLiteral());
            case $GTE:
                return criteria.gte(value.getLiteral());
            case $IN:
                return criteria.in(((ListQueryLiteral) value).getLiterals());
            case $NIN:
                return criteria.nin(((ListQueryLiteral) value).getLiterals());
            case $LT:
                return criteria.lt(value.getLiteral());
            case $LTE:
                return criteria.lte(value.getLiteral());
            case $NE:
                return criteria.ne(value.getLiteral());
            case $LIKE:
                return criteria.regex((String) value.getLiteral(), "i"); // i means case insensitive
            case $ELEM_MATCH:
                return criteria.elemMatch(buildFromResourceQuery((ResourceQuery) value.getLiteral()));
            case $EXISTS:
                return criteria.exists((Boolean) value.getLiteral());
            case $SIZE:
                return criteria.size(((Long) value.getLiteral()).intValue());
        }
        return criteria;
    }

}
