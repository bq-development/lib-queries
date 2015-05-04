package com.bq.oss.lib.queries.request;

import org.junit.Test;

import java.util.Iterator;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Rubén Carrasco
 *
 */
public class CountOperationTest {

	@Test
	public void testAll() {
		Count operation = new Count("*");
		ResourceQuery res = operation.operate(null);
		assertThat(res.iterator().hasNext()).isEqualTo(false);
	}

	@Test
	public void testField() {
		Count operation = new Count("asdf");
		ResourceQuery res = operation.operate(null);
		Iterator<QueryNode> iterator = res.iterator();
		assertThat(iterator.hasNext()).isEqualTo(true);
		QueryNode next = iterator.next();
		assertThat(next.getField()).isEqualTo("asdf");
		assertThat(next.getOperator()).isEqualTo(QueryOperator.$EXISTS);
		assertThat(iterator.hasNext()).isEqualTo(false);
	}

}
