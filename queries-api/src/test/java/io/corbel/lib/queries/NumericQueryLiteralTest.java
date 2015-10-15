package io.corbel.lib.queries;

import io.corbel.lib.queries.exception.QueryMatchingException;
import io.corbel.lib.queries.request.QueryLiteral;
import io.corbel.lib.queries.request.QueryOperator;
import org.junit.Test;

import java.util.Collections;

import static org.fest.assertions.api.Assertions.assertThat;

public class NumericQueryLiteralTest {

    @Test
    public void eqTest() throws QueryMatchingException {
        QueryLiteral<Long> literal = new LongQueryLiteral(123l);
        assertThat(literal.operate(QueryOperator.$EQ, 123l)).isTrue();
        assertThat(literal.operate(QueryOperator.$EQ, 123)).isTrue();
        assertThat(literal.operate(QueryOperator.$EQ, 123.0)).isTrue();

        assertThat(literal.operate(QueryOperator.$EQ, 123.1)).isFalse();
        assertThat(literal.operate(QueryOperator.$EQ, 124l)).isFalse();
    }

    @Test
    public void sizeTest() throws QueryMatchingException {
        QueryLiteral<Long> literal = new LongQueryLiteral(0l);
        assertThat(literal.operate(QueryOperator.$SIZE, Collections.emptyList())).isTrue();
        QueryLiteral<Long> literal2 = new LongQueryLiteral(1l);
        assertThat(literal2.operate(QueryOperator.$SIZE, Collections.emptyList())).isFalse();
    }

    @Test
    public void comparationsTest() throws QueryMatchingException {
        QueryLiteral<Long> literal = new LongQueryLiteral(123l);
        assertThat(literal.operate(QueryOperator.$GT, 124l)).isTrue();
        assertThat(literal.operate(QueryOperator.$GT, 124.1)).isTrue();
        assertThat(literal.operate(QueryOperator.$GT, 123)).isFalse();
        assertThat(literal.operate(QueryOperator.$GT, 100)).isFalse();

        assertThat(literal.operate(QueryOperator.$GTE, 124l)).isTrue();
        assertThat(literal.operate(QueryOperator.$GTE, 124.1)).isTrue();
        assertThat(literal.operate(QueryOperator.$GTE, 123)).isTrue();
        assertThat(literal.operate(QueryOperator.$GTE, 100)).isFalse();

        assertThat(literal.operate(QueryOperator.$LT, 124l)).isFalse();
        assertThat(literal.operate(QueryOperator.$LT, 124.1)).isFalse();
        assertThat(literal.operate(QueryOperator.$LT, 123)).isFalse();
        assertThat(literal.operate(QueryOperator.$LT, 100)).isTrue();

        assertThat(literal.operate(QueryOperator.$LTE, 124l)).isFalse();
        assertThat(literal.operate(QueryOperator.$LTE, 124.1)).isFalse();
        assertThat(literal.operate(QueryOperator.$LTE, 123)).isTrue();
        assertThat(literal.operate(QueryOperator.$LTE, 100)).isTrue();
    }

    @Test(expected = QueryMatchingException.class)
    public void notAllowedOperatorTest() throws QueryMatchingException {
        QueryLiteral<Long> literal = new LongQueryLiteral(123l);
        literal.operate(QueryOperator.$EQ, "bad type");
    }
}
