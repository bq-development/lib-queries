package com.bq.oss.lib.queries.builder;

import com.bq.oss.lib.queries.request.QueryNode;
import com.bq.oss.lib.queries.request.QueryOperator;
import com.bq.oss.lib.queries.request.ResourceQuery;
import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Rubén Carrasco
 *
 */
public class ResourceQueryBuilderTest {

	private static final String FIELD = "field";
	private static final String VALUE = "value";

	@Test
	public void testAddWithOp() {
		ResourceQuery query = new ResourceQueryBuilder().add(FIELD, VALUE, QueryOperator.$EQ).build();
		QueryNode node = query.iterator().next();
		assertThat((String) node.getValue().getLiteral()).isEqualTo(VALUE);
		assertThat(node.getOperator()).isEqualTo(QueryOperator.$EQ);
	}

	@Test
	public void testAddWithNoOp() {
		ResourceQuery query = new ResourceQueryBuilder().add(FIELD, VALUE).build();
		QueryNode node = query.iterator().next();
		assertThat((String) node.getValue().getLiteral()).isEqualTo(VALUE);
		assertThat(node.getOperator()).isEqualTo(QueryOperator.$EQ);
	}

	@Test
	public void testRemove() throws MalformedJsonQueryException {
		ResourceQuery resourceQuery = new ResourceQueryBuilder().add(FIELD, VALUE, QueryOperator.$IN).build();
		assertThat(resourceQuery.iterator().hasNext()).isTrue();
		resourceQuery = new ResourceQueryBuilder(resourceQuery).remove(FIELD).build();
		assertThat(resourceQuery.iterator().hasNext()).isFalse();
	}

	@Test
	public void testRemoveEmpty() {
		ResourceQuery query = new ResourceQueryBuilder().remove(FIELD).build();
		assertThat(query.iterator().hasNext()).isFalse();
	}

}
