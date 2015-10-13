package io.corbel.lib.queries.request;

import io.corbel.lib.queries.exception.QueryMatchingException;
import io.corbel.lib.queries.matcher.QueryMatcher;

public abstract class QueryLiteral<T> {

    protected T literal;

    public QueryLiteral() {}

    public QueryLiteral(T literal) {
        this.literal = literal;
    }

    public T getLiteral() {
        return literal;
    }

    public void setLiteral(T literal) {
        this.literal = literal;
    }

    public boolean operate(QueryOperator operator, Object object) throws QueryMatchingException {
        return operate(operator, object, null);
    }

    public boolean operate(QueryOperator operator, Object object, QueryMatcher queryMatcher) throws QueryMatchingException {

        try {
            switch (operator) {
                case $ALL:
                    return this.all(object);
                case $ELEM_MATCH:
                    return this.elemMatch(object, queryMatcher);
                case $EQ:
                    return this.eq(object);
                case $GT:
                    return this.gt(object);
                case $GTE:
                    return this.gte(object);
                case $IN:
                    return this.in(object);
                case $NIN:
                    return this.nin(object);
                case $LIKE:
                    return this.like(object);
                case $LT:
                    return this.lt(object);
                case $LTE:
                    return this.lte(object);
                case $NE:
                    return this.ne(object);
                case $SIZE:
                    return this.size(object);
                default:
                    return false;
            }
        } catch (ClassCastException | NumberFormatException e) {
            throw new QueryMatchingException("The literal and the object type not matches, ", e);
        }
    }

    protected boolean eq(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$EQ));
    }

    protected boolean all(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$ALL));
    }

    protected boolean elemMatch(Object object, QueryMatcher queryMatcher) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$ELEM_MATCH));
    }

    protected boolean gt(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$GT));
    }

    protected boolean gte(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$GTE));
    }

    protected boolean in(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$IN));
    }

    protected boolean nin(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$NIN));
    }

    protected boolean like(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$LIKE));
    }

    protected boolean lt(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$LT));
    }

    protected boolean lte(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$LTE));
    }

    protected boolean ne(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$NE));
    }

    protected boolean size(Object object) throws QueryMatchingException {
        throw new QueryMatchingException(getExceptionMessage(QueryOperator.$SIZE));
    }

    private String getExceptionMessage(QueryOperator queryOperator) throws QueryMatchingException {
        return "Operator " + queryOperator.name() + " not suported for matching with literal " + literal.getClass().getName();
    }

}
