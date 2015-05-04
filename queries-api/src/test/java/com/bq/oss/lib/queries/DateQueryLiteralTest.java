package com.bq.oss.lib.queries;

import static org.fest.assertions.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.Test;

import com.bq.oss.lib.queries.exception.QueryMatchingException;
import com.bq.oss.lib.queries.request.QueryLiteral;
import com.bq.oss.lib.queries.request.QueryOperator;

public class DateQueryLiteralTest {

	@Test
	public void toStringTest() {
		assertThat(new DateQueryLiteral(new Date(1413807960000l)).toString()).isEqualTo(
				"\"ISODate(2014-10-20T12:26:00Z)\"");
	}

	@Test
	public void eqTest() throws QueryMatchingException {
		Instant instant = Instant.now();
		QueryLiteral<Date> literal = new DateQueryLiteral(Date.from(instant));
		assertThat(literal.operate(QueryOperator.$EQ, Date.from(instant))).isTrue();

		instant.minus(1, ChronoUnit.SECONDS);
		assertThat(literal.operate(QueryOperator.$EQ, Date.from(instant.minus(1, ChronoUnit.SECONDS)))).isFalse();
	}

	@Test
	public void comparationsTest() throws QueryMatchingException {

		Instant instant = Instant.now();
		QueryLiteral<Date> literal = new DateQueryLiteral(Date.from(instant));
		assertThat(literal.operate(QueryOperator.$LTE, Date.from(instant))).isTrue();
		assertThat(literal.operate(QueryOperator.$GTE, Date.from(instant))).isTrue();
		assertThat(literal.operate(QueryOperator.$LT, Date.from(instant))).isFalse();
		assertThat(literal.operate(QueryOperator.$GT, Date.from(instant))).isFalse();

		Instant instant2 = instant.minus(1, ChronoUnit.DAYS);
		assertThat(literal.operate(QueryOperator.$LTE, Date.from(instant2))).isTrue();
		assertThat(literal.operate(QueryOperator.$GTE, Date.from(instant2))).isFalse();
		assertThat(literal.operate(QueryOperator.$LT, Date.from(instant2))).isTrue();
		assertThat(literal.operate(QueryOperator.$GT, Date.from(instant2))).isFalse();

		Instant instant3 = instant.plus(1, ChronoUnit.DAYS);
		assertThat(literal.operate(QueryOperator.$LTE, Date.from(instant3))).isFalse();
		assertThat(literal.operate(QueryOperator.$GTE, Date.from(instant3))).isTrue();
		assertThat(literal.operate(QueryOperator.$LT, Date.from(instant3))).isFalse();
		assertThat(literal.operate(QueryOperator.$GT, Date.from(instant3))).isTrue();
	}
}
