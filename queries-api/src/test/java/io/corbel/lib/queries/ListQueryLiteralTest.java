package io.corbel.lib.queries;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Arrays;

import io.corbel.lib.queries.exception.QueryMatchingException;
import org.junit.Test;

public class ListQueryLiteralTest {

	@Test
	public void inTest() throws QueryMatchingException {
		assertThat(
				new ListQueryLiteral(Arrays.asList(new LongQueryLiteral(1l), new LongQueryLiteral(2l),
						new LongQueryLiteral(3l))).in(2)).isTrue();
		assertThat(
				new ListQueryLiteral(Arrays.asList(new LongQueryLiteral(1l), new LongQueryLiteral(2l),
						new LongQueryLiteral(3l))).in(4)).isFalse();
		assertThat(
				new ListQueryLiteral(Arrays.asList(new StringQueryLiteral("uno"), new StringQueryLiteral("dos"),
						new StringQueryLiteral("tres"))).in("dos")).isTrue();
		assertThat(
				new ListQueryLiteral(Arrays.asList(new StringQueryLiteral("uno"), new StringQueryLiteral("dos"),
						new StringQueryLiteral("tres"))).in("core")).isFalse();
	}

	@Test
	public void eqTest() throws QueryMatchingException {
		assertThat(
				new ListQueryLiteral(Arrays.asList(new LongQueryLiteral(1l), new LongQueryLiteral(2l),
						new LongQueryLiteral(3l))).eq(Arrays.asList(1, 2, 3))).isTrue();
		assertThat(
				new ListQueryLiteral(Arrays.asList(new LongQueryLiteral(1l), new LongQueryLiteral(2l),
						new LongQueryLiteral(3l))).eq(Arrays.asList(1, 2, 4))).isFalse();
		assertThat(
				new ListQueryLiteral(Arrays.asList(new LongQueryLiteral(1l), new LongQueryLiteral(2l),
						new LongQueryLiteral(3l))).eq(Arrays.asList(1, 2))).isFalse();
	}

	@Test
	public void allTest() throws QueryMatchingException {
		assertThat(
				new ListQueryLiteral(Arrays.asList(new LongQueryLiteral(1l), new LongQueryLiteral(2l),
						new LongQueryLiteral(3l))).all(Arrays.asList(1, 2, 3, 4))).isTrue();
		assertThat(
				new ListQueryLiteral(Arrays.asList(new LongQueryLiteral(1l), new LongQueryLiteral(2l),
						new LongQueryLiteral(3l))).all(Arrays.asList(1, 2, 3))).isTrue();
		assertThat(
				new ListQueryLiteral(Arrays.asList(new LongQueryLiteral(1l), new LongQueryLiteral(2l),
						new LongQueryLiteral(3l))).all(Arrays.asList(1, 2, 4))).isFalse();
		assertThat(
				new ListQueryLiteral(Arrays.asList(new LongQueryLiteral(1l), new LongQueryLiteral(2l),
						new LongQueryLiteral(3l))).all(Arrays.asList(1, 2))).isFalse();
	}
}
