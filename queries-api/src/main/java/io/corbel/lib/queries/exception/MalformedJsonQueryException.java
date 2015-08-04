package io.corbel.lib.queries.exception;

public class MalformedJsonQueryException extends Exception {

    private static final long serialVersionUID = 832078486184551880L;

    public MalformedJsonQueryException(String message, Exception e) {
        super(message, e);
    }

    public MalformedJsonQueryException(String message) {
        super(message);
    }

    public MalformedJsonQueryException(Exception e) {
        super(e);
    }

}
