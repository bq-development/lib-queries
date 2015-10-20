package io.corbel.lib.queries;

import io.corbel.lib.queries.exception.QueryMatchingException;

import java.util.List;

public class LongQueryLiteral extends NumericQueryLiteral<Long> {
    public LongQueryLiteral() {
        super();
    }

    public LongQueryLiteral(Long literal) {
        super(literal);
    }

    @Override
    protected boolean size(Object object) throws QueryMatchingException {
        List objectList = (List) object;
        return eq(objectList.size());
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
        LongQueryLiteral other = (LongQueryLiteral) obj;
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
        return Long.toString(getLiteral());
    }
}
