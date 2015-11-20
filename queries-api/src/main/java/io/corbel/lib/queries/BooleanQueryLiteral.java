package io.corbel.lib.queries;

import io.corbel.lib.queries.exception.QueryMatchingException;
import io.corbel.lib.queries.request.QueryLiteral;

public class BooleanQueryLiteral extends QueryLiteral<Boolean> {

    public BooleanQueryLiteral() {
        super();
    }

    public BooleanQueryLiteral(Boolean bool) {
        super(bool);
    }

    @Override
    protected boolean eq(Object object) throws QueryMatchingException {
        return literal.equals(object);
    };

    @Override
    protected boolean ne(Object object) throws QueryMatchingException {
        return !literal.equals(object);
    };

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
        BooleanQueryLiteral other = (BooleanQueryLiteral) obj;
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
        return Boolean.toString(getLiteral());
    }

}
