package io.corbel.lib.queries.exception;

/**
 * @author Alexander De Leon
 */
public class InvalidParameterException extends IllegalArgumentException {

    public enum Parameter {
        AGGREGATION, QUERY, SORT, PAGE_SIZE, PAGE, SEARCH
    }

    private final Parameter parameter;
    private final Object value;

    public InvalidParameterException(Parameter parameter, Object value, String message, Exception cause) {
        super(message, cause);
        this.parameter = parameter;
        this.value = value;
    }

    public InvalidParameterException(Parameter parameter, Object value, String message) {
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
