package io.corbel.lib.queries.builder;

import io.corbel.lib.queries.request.QueryNode;
import io.corbel.lib.queries.request.QueryOperator;
import io.corbel.lib.queries.request.ResourceQuery;
import io.corbel.lib.queries.exception.MalformedJsonQueryException;
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
    public void testAddSizeOp(){
        ResourceQuery query = new ResourceQueryBuilder().add(FIELD, VALUE, QueryOperator.$SIZE).build();
        QueryNode node = query.iterator().next();
        assertThat((String) node.getValue().getLiteral()).isEqualTo(VALUE);
        assertThat(node.getOperator()).isEqualTo(QueryOperator.$SIZE);
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
