package com.bq.oss.lib.queries.builder;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bq.oss.lib.queries.BooleanQueryLiteral;
import com.bq.oss.lib.queries.DateQueryLiteral;
import com.bq.oss.lib.queries.DoubleQueryLiteral;
import com.bq.oss.lib.queries.ListQueryLiteral;
import com.bq.oss.lib.queries.LongQueryLiteral;
import com.bq.oss.lib.queries.QueryNodeImpl;
import com.bq.oss.lib.queries.StringQueryLiteral;
import com.bq.oss.lib.queries.request.QueryNode;
import com.bq.oss.lib.queries.request.QueryOperator;
import com.bq.oss.lib.queries.request.ResourceQuery;

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
        BooleanQueryLiteral literal = new BooleanQueryLiteral();
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
        ListQueryLiteral literal = new ListQueryLiteral(value);
        QueryNodeImpl queryNode = new QueryNodeImpl(operator, field, literal);
        resourceQuery.addQueryNode(queryNode);
        return this;
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
