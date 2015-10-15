package io.corbel.lib.queries.request;

public interface QueryNode {

    QueryOperator getOperator();

    String getField();

    QueryLiteral<?> getValue();
}
