package io.corbel.lib.queries.builder;

import io.corbel.lib.queries.BooleanQueryLiteral;
import io.corbel.lib.queries.DateQueryLiteral;
import io.corbel.lib.queries.DoubleQueryLiteral;
import io.corbel.lib.queries.ListQueryLiteral;
import io.corbel.lib.queries.LongQueryLiteral;
import io.corbel.lib.queries.QueryNodeImpl;
import io.corbel.lib.queries.StringQueryLiteral;
import io.corbel.lib.queries.request.QueryLiteral;
import io.corbel.lib.queries.request.QueryNode;
import io.corbel.lib.queries.request.QueryOperator;
import io.corbel.lib.queries.request.ResourceQuery;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rubén Carrasco
 *
 */
public class ResourceQueryBuilder {

    private final ResourceQuery resourceQuery;

    /**
     * Constructs a ResourceQueryBuilder with the given ResourceQuery
     * 
     * @param resourceQuery to create the ResourceQueryBuilder
     */
    public ResourceQueryBuilder(ResourceQuery resourceQuery) {
        if (resourceQuery == null) {
            this.resourceQuery = new ResourceQuery();
        } else {
            this.resourceQuery = resourceQuery;
        }
    }

    public ResourceQueryBuilder() {
        this(null);
    }

    /**
     * Adds a query node with a String value to the {@link ResourceQuery}
     * 
     * @param field object of the operation
     * @param value of the node
     * @param operator one of [$EQ, $NE, $LIKE]
     * @return ResourceQueryBuilder
     */
    public ResourceQueryBuilder add(String field, String value, QueryOperator operator) {
        StringQueryLiteral literal = new StringQueryLiteral(value);
        QueryNodeImpl queryNode = new QueryNodeImpl(operator, field, literal);
        resourceQuery.addQueryNode(queryNode);
        return this;
    }

    /**
     * Adds a query node with String value and $EQ operator to the {@link ResourceQuery}
     * 
     * @param field object of the operation
     * @param value of the node
     * @return ResourceQueryBuilder
     */
    public ResourceQueryBuilder add(String field, String value) {
        return add(field, value, QueryOperator.$EQ);
    }

    /**
     * Adds a query node with a Date value to the {@link ResourceQuery}
     * 
     * @param field object of the operation
     * @param value of the node
     * @param operator one of [$EQ, $NE, $GT, $GTE, $LT, $LTE]
     * @return ResourceQueryBuilder
     */
    public ResourceQueryBuilder add(String field, Date value, QueryOperator operator) {
        DateQueryLiteral literal = new DateQueryLiteral(value);
        QueryNodeImpl queryNode = new QueryNodeImpl(operator, field, literal);
        resourceQuery.addQueryNode(queryNode);
        return this;
    }

    /**
     * Adds a query node with a Boolean value to the {@link ResourceQuery}
     * 
     * @param field object of the operation
     * @param operator one of [$EQ, $NE]
     * @return ResourceQueryBuilder
     */
    public ResourceQueryBuilder add(String field, QueryOperator operator) {
        return this.add(field, Boolean.TRUE, operator);
    }

    /**
     * Adds a query node with a Boolean value to the {@link ResourceQuery}
     *
     * @param field object of the operation
     * @param operator one of [$EQ, $NE, $EXISTS]
     * @return ResourceQueryBuilder
     */
    public ResourceQueryBuilder add(String field, Boolean bool, QueryOperator operator) {
        BooleanQueryLiteral literal = new BooleanQueryLiteral();
        literal.setLiteral(bool);
        QueryNodeImpl queryNode = new QueryNodeImpl(operator, field, literal);
        resourceQuery.addQueryNode(queryNode);
        return this;
    }


    /**
     * Adds a query node with a List value to the {@link ResourceQuery}
     * 
     * @param field object of the operation
     * @param value of the node
     * @param operator one of [$EQ, $NE, $IN, $ALL]
     * @return ResourceQueryBuilder
     */
    public ResourceQueryBuilder add(String field, List value, QueryOperator operator) {
        ListQueryLiteral literal;

        if (value == null || value.isEmpty()) {
            literal = new ListQueryLiteral(value);
        } else {
            literal = new ListQueryLiteral(listToQueryLiteralList(value));
        }

        QueryNodeImpl queryNode = new QueryNodeImpl(operator, field, literal);
        resourceQuery.addQueryNode(queryNode);
        return this;
    }

    private List listToQueryLiteralList(List list) {
        Object elem = list.get(0);
        if (elem instanceof String) {
            return ((List<String>) list).stream().map(e -> new StringQueryLiteral(e)).collect(Collectors.toList());
        } else if (elem instanceof Boolean) {
            return ((List<Boolean>) list).stream().map(e -> new BooleanQueryLiteral(e)).collect(Collectors.toList());
        } else if (elem instanceof Double) {
            return ((List<Double>) list).stream().map(e -> new DoubleQueryLiteral(e)).collect(Collectors.toList());
        } else if (elem instanceof Long) {
            return ((List<Long>) list).stream().map(e -> new LongQueryLiteral(e)).collect(Collectors.toList());
        } else if (elem instanceof Integer) {
            return ((List<Integer>) list).stream().map(e -> new LongQueryLiteral(new Long(e))).collect(Collectors.toList());
        } else if (elem instanceof Date) {
            return ((List<Date>) list).stream().map(e -> new DateQueryLiteral(e)).collect(Collectors.toList());
        } else if (elem instanceof QueryLiteral) {
            return list;
        } else if (elem instanceof List) {
            return ((List<List>) list).stream().map(e -> new ListQueryLiteral(e)).collect(Collectors.toList());
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Adds a query node with a Double value to the {@link ResourceQuery}
     * 
     * @param field object of the operation
     * @param value of the node
     * @param operator one of [$EQ, $NE, $GT, $GTE, $LT, $LTE]
     * @return ResourceQueryBuilder
     */
    public ResourceQueryBuilder add(String field, Double value, QueryOperator operator) {
        DoubleQueryLiteral literal = new DoubleQueryLiteral(value);
        QueryNodeImpl queryNode = new QueryNodeImpl(operator, field, literal);
        resourceQuery.addQueryNode(queryNode);
        return this;
    }

    /**
     * Adds a query node with a Long value to the {@link ResourceQuery}
     * 
     * @param field object of the operation
     * @param value of the node
     * @param operator one of [$EQ, $NE, $GT, $GTE, $LT, $LTE]
     * @return ResourceQueryBuilder
     */
    public ResourceQueryBuilder add(String field, Long value, QueryOperator operator) {
        LongQueryLiteral literal = new LongQueryLiteral(value);
        QueryNodeImpl queryNode = new QueryNodeImpl(operator, field, literal);
        resourceQuery.addQueryNode(queryNode);
        return this;
    }

    /**
     * Removes all nodes from the ResourceQuery whose field is given field
     * 
     * @param field object of the operation
     * @return ResourceQueryBuilder
     */
    public ResourceQueryBuilder remove(String field) {
        Iterator<QueryNode> queryNodeIterator = resourceQuery.iterator();
        while (queryNodeIterator.hasNext()) {
            QueryNode queryNode = queryNodeIterator.next();
            if (queryNode.getField().matches(field)) {
                queryNodeIterator.remove();
            }
        }
        return this;
    }

    /**
     * Builds a ResourceQuery
     * 
     * @return ResourceQuery
     */
    public ResourceQuery build() {
        return resourceQuery;
    }
}
