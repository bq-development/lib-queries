package com.bq.oss.lib.queries.exception;

/**
 * @author Alexander De Leon me@alexdeleon.name
 */
public class InvalidParameterException extends IllegalArgumentException {
    public static enum Parameter {
        AGGREGATION,
        QUERY,
        SORT,
        PAGE_SIZE,
        PAGE
    }

    private final Parameter parameter;
    private final Object value;

    public InvalidParameterException(Parameter parameter, Object value, String message, Exception cause){
        super(message, cause);
        this.parameter = parameter;
        this.value = value;
    }

    public InvalidParameterException(Parameter parameter, Object value, String message){
        super(message);
        this.parameter = parameter;
        this.value = value;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public Object getValue() {
        return value;
    }
}
