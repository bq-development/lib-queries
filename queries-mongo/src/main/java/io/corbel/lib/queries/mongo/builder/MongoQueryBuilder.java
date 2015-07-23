package io.corbel.lib.queries.mongo.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.corbel.lib.mongo.SafeKeys;
import io.corbel.lib.queries.request.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import io.corbel.lib.queries.ListQueryLiteral;
import io.corbel.lib.queries.builder.QueryBuilder;

/**
 * @author Alberto J. Rubio
 */
public class MongoQueryBuilder implements QueryBuilder {

    protected final Query query;

    public MongoQueryBuilder() {
        query = new Query();
    }

    @Override
    public QueryBuilder query(ResourceQuery resourceQuery) {
        if (resourceQuery != null) {
            query.addCriteria(getCriteriaFromResourceQuery(resourceQuery));
        }
        return this;
    }

    @Override
    public QueryBuilder query(List<ResourceQuery> resourceQueries) {
        if (resourceQueries != null && !resourceQueries.isEmpty()) {
            query.addCriteria(getCriteriaFromResourceQueries(resourceQueries));
        }
        return this;
    }

    public Criteria getCriteriaFromResourceQueries(List<ResourceQuery> resourceQueries) {
        List<Criteria> criterias = new ArrayList<>();
        for (ResourceQuery resourceQuery : resourceQueries) {
            criterias.add(getCriteriaFromResourceQuery(resourceQuery));
        }

        if (criterias.size() == 0) {
            return new Criteria();
        } else if (criterias.size() == 1) {
            return criterias.get(0);
        } else {
            return new Criteria().orOperator(criterias.toArray(new Criteria[criterias.size()]));
        }
    }

    @Override
    public QueryBuilder pagination(Pagination pagination) {
        if (pagination != null) {
            query.with(new PageRequest(pagination.getPage(), pagination.getPageSize()));
        }
        return this;
    }

    @Override
    public QueryBuilder sort(io.corbel.lib.queries.request.Sort sort) {
        if (sort != null) {
            query.with(new Sort(Direction.fromString(sort.getDirection().name()), sort.getField()));
        }
        return this;
    }

    @Override
    public Query build() {
        return query;
    }

    public Criteria getCriteriaFromResourceQuery(ResourceQuery resourceQuery) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        for (QueryNode queryNode : resourceQuery) {
            String safeQuery = Arrays.stream(queryNode.getField().split("[.]")).map(SafeKeys::getSafeKey).collect(Collectors.joining("."));

            criterias.add(criteria(queryNode.getOperator(), safeQuery, queryNode.getValue()));
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

    private Criteria criteria(QueryOperator operator, String field, QueryLiteral<?> value) {
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
                return criteria.elemMatch(getCriteriaFromResourceQuery((ResourceQuery) value.getLiteral()));
            case $EXISTS:
                return criteria.exists((Boolean) value.getLiteral());
        }
        return criteria;
    }

}
