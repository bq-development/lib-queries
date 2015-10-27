package io.corbel.lib.queries.request;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Rubén Carrasco
 *
 */
public class FieldAggregationTest {

    @Before
    public void setUp() throws Exception {}

    @Test
    public void test() {
        Count count = new Count("asdf");
        Count count2 = new Count("asdf");
        assertThat(count.equals(count2)).isTrue();

        count2 = new Count("qwer");
        assertThat(count.equals(count2)).isFalse();

        Sum sum = new Sum("asdf");
        assertThat(count.equals(sum)).isFalse();
    }

}
