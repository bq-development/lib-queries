package io.corbel.lib.queries;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.corbel.lib.queries.exception.QueryMatchingException;
import io.corbel.lib.queries.request.QueryLiteral;

public class StringQueryLiteral extends QueryLiteral<String> {

    public StringQueryLiteral() {
        super();
    }

    public StringQueryLiteral(String literal) {
        super(literal);
    }

    @Override
    protected boolean eq(Object object) throws QueryMatchingException {
        if (object instanceof Collection) {
            for(Object entry : (Collection) object) {
                if (literal.equals(entry.toString())) {
                    return true;
                }
            }
            return false;
        } else {
            return object.toString().equals(literal);
        }
    }

    @Override
    protected boolean ne(Object object) throws QueryMatchingException {
        return !eq(object);
    }

    @Override
    protected boolean like(Object object) throws QueryMatchingException {
        Pattern pattern = Pattern.compile(literal, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        if (object instanceof Collection) {
            for(Object entry : (Collection) object) {
                matcher = pattern.matcher(entry.toString());
                if (matcher.matches()) {
                    return true;
                }
            }
            return false;
        } else {
            String stringObject = object.toString();
            matcher = pattern.matcher(stringObject);
            return matcher.matches();
        }
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
        StringQueryLiteral other = (StringQueryLiteral) obj;
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
        return "\"" + getLiteral() + "\"";
    }
}
