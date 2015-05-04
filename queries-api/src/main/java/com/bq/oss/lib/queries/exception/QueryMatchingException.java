package com.bq.oss.lib.queries.exception;

public class QueryMatchingException extends Exception {

	private static final long serialVersionUID = -1336015396888143403L;

	public QueryMatchingException(String message, Exception e) {
		super(message, e);
	}

	public QueryMatchingException(String message) {
		super(message);
	}

	public QueryMatchingException(Exception e) {
		super(e);
	}
}
