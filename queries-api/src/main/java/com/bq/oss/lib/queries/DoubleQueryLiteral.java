package com.bq.oss.lib.queries;


public class DoubleQueryLiteral extends NumericQueryLiteral<Double> {

    public DoubleQueryLiteral() {
        super();
    }

    public DoubleQueryLiteral(Double literal) {
        super(literal);
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
        DoubleQueryLiteral other = (DoubleQueryLiteral) obj;
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
        return Double.toString(getLiteral());
    }
}
