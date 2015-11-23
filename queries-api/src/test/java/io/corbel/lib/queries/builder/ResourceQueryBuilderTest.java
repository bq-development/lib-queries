package io.corbel.lib.queries.builder;

import static org.fest.assertions.api.Assertions.assertThat;
import io.corbel.lib.queries.ListQueryLiteral;
import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.QueryNode;
import io.corbel.lib.queries.request.QueryOperator;
import io.corbel.lib.queries.request.ResourceQuery;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

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
    public void testAddSizeOp() {
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

    @Test
    public void testAddStringList() {
        List<String> list = Arrays.asList("asdf", "qwer");
        ResourceQuery query = new ResourceQueryBuilder().add(FIELD, list, QueryOperator.$IN).build();
        QueryNode node = query.iterator().next();
        ListQueryLiteral listQueryLiteral = (ListQueryLiteral) node.getValue();
        assertThat(listQueryLiteral.getLiterals().get(0)).isEqualTo("asdf");
        assertThat(listQueryLiteral.getLiterals().get(1)).isEqualTo("qwer");
        assertThat(node.getOperator()).isEqualTo(QueryOperator.$IN);
    }

    @Test
    public void testAddBooleanList() {
        List<Boolean> list = Arrays.asList(true, false);
        ResourceQuery query = new ResourceQueryBuilder().add(FIELD, list, QueryOperator.$IN).build();
        QueryNode node = query.iterator().next();
        ListQueryLiteral listQueryLiteral = (ListQueryLiteral) node.getValue();
        assertThat(listQueryLiteral.getLiterals().get(0)).isEqualTo(true);
        assertThat(listQueryLiteral.getLiterals().get(1)).isEqualTo(false);
        assertThat(node.getOperator()).isEqualTo(QueryOperator.$IN);
    }

    @Test
    public void testAddDoubleList() {
        List<Double> list = Arrays.asList(1.2d, 2.3d);
        ResourceQuery query = new ResourceQueryBuilder().add(FIELD, list, QueryOperator.$IN).build();
        QueryNode node = query.iterator().next();
        ListQueryLiteral listQueryLiteral = (ListQueryLiteral) node.getValue();
        assertThat(listQueryLiteral.getLiterals().get(0)).isEqualTo(1.2d);
        assertThat(listQueryLiteral.getLiterals().get(1)).isEqualTo(2.3d);
        assertThat(node.getOperator()).isEqualTo(QueryOperator.$IN);
    }

    @Test
    public void testAddLongList() {
        List<Long> list = Arrays.asList(1l, 3l);
        ResourceQuery query = new ResourceQueryBuilder().add(FIELD, list, QueryOperator.$IN).build();
        QueryNode node = query.iterator().next();
        ListQueryLiteral listQueryLiteral = (ListQueryLiteral) node.getValue();
        assertThat(listQueryLiteral.getLiterals().get(0)).isEqualTo(1l);
        assertThat(listQueryLiteral.getLiterals().get(1)).isEqualTo(3l);
        assertThat(node.getOperator()).isEqualTo(QueryOperator.$IN);
    }

    @Test
    public void testAddIntegerList() {
        List<Integer> list = Arrays.asList(1, 3);
        ResourceQuery query = new ResourceQueryBuilder().add(FIELD, list, QueryOperator.$IN).build();
        QueryNode node = query.iterator().next();
        ListQueryLiteral listQueryLiteral = (ListQueryLiteral) node.getValue();
        assertThat(listQueryLiteral.getLiterals().get(0)).isEqualTo(1l);
        assertThat(listQueryLiteral.getLiterals().get(1)).isEqualTo(3l);
        assertThat(node.getOperator()).isEqualTo(QueryOperator.$IN);
    }

    @Test
    public void testAddDateList() {
        Date date = new Date();
        List<Date> list = Arrays.asList(date);
        ResourceQuery query = new ResourceQueryBuilder().add(FIELD, list, QueryOperator.$IN).build();
        QueryNode node = query.iterator().next();
        ListQueryLiteral listQueryLiteral = (ListQueryLiteral) node.getValue();
        assertThat(listQueryLiteral.getLiterals().get(0)).isEqualTo(date);
        assertThat(node.getOperator()).isEqualTo(QueryOperator.$IN);
    }

    @Test
    public void testAddListList() {
        List<Integer> list1 = Arrays.asList(1, 3);
        List<Integer> list2 = Arrays.asList(2, 4);
        List<List> list = Arrays.asList(list1, list2);

        ResourceQuery query = new ResourceQueryBuilder().add(FIELD, list, QueryOperator.$IN).build();
        QueryNode node = query.iterator().next();
        ListQueryLiteral listQueryLiteral = (ListQueryLiteral) node.getValue();

        List listQueryLiteral1 = (List) listQueryLiteral.getLiterals().get(0);
        assertThat(listQueryLiteral1.get(0)).isEqualTo(1);
        assertThat(listQueryLiteral1.get(1)).isEqualTo(3);

        List listQueryLiteral2 = (List) listQueryLiteral.getLiterals().get(1);
        assertThat(listQueryLiteral2.get(0)).isEqualTo(2);
        assertThat(listQueryLiteral2.get(1)).isEqualTo(4);

        assertThat(node.getOperator()).isEqualTo(QueryOperator.$IN);
    }

}
