package io.corbel.lib.queries.request;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * @author Rubén Carrasco
 *
 */
public class CountOperationTest {

    @Test
    public void testAll() {
        Count operation = new Count("*");
        List<ResourceQuery> res = operation.operate(null);
        assertThat(res.get(0).iterator().hasNext()).isEqualTo(false);
    }

    @Test
    public void testField() {
        Count operation = new Count("asdf");
        List<ResourceQuery> res = operation.operate(null);
        Iterator<QueryNode> iterator = res.get(0).iterator();
        assertThat(iterator.hasNext()).isEqualTo(true);
        QueryNode next = iterator.next();
        assertThat(next.getField()).isEqualTo("asdf");
        assertThat(next.getOperator()).isEqualTo(QueryOperator.$EXISTS);
        assertThat(iterator.hasNext()).isEqualTo(false);
    }

}
