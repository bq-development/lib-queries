package io.corbel.lib.queries.parser;

import static org.fest.assertions.api.Assertions.assertThat;
import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.aggregation.Aggregation;
import io.corbel.lib.queries.request.aggregation.AggregationOperator;
import io.corbel.lib.queries.request.aggregation.Average;
import io.corbel.lib.queries.request.aggregation.Max;
import io.corbel.lib.queries.request.aggregation.Min;
import io.corbel.lib.queries.request.aggregation.Sum;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Rubén Carrasco
 *
 */
public class JacksonAggregationParserTest {

    JacksonAggregationParser parser = new JacksonAggregationParser(new CustomJsonParser(new ObjectMapper().getFactory()));

    @Test
    public void test() throws MalformedJsonQueryException {
        String opString = "{\"$count\":\"xxxx\"}";
        Aggregation operation = parser.parse(opString);
        assertThat(operation.getOperator()).isEqualTo(AggregationOperator.$COUNT);
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testMultipleOperators() throws MalformedJsonQueryException {
        String opString = "{\"$count\":\"xxxx\", \"$sum\":\"asdf\"}";
        parser.parse(opString);
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testArrayOfOperators() throws MalformedJsonQueryException {
        String opString = "[{\"$count\":\"xxxx\"}, {\"$count\":\"asdf\"}]";
        parser.parse(opString);
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testNonExistentOperator() throws MalformedJsonQueryException {
        String opString = "{\"$asdf\":\"xxxx\"}";
        parser.parse(opString);
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testMalformedJson() throws MalformedJsonQueryException {
        String opString = "{\"$count\":xxxx\"}";
        parser.parse(opString);
    }

    @Test
    public void testAverage() throws MalformedJsonQueryException {
        String opString = "{\"$avg\":\"xxxx\"}";
        Aggregation operation = parser.parse(opString);
        assertThat(operation).isInstanceOf(Average.class);
        assertThat(operation.getOperator()).isEqualTo(AggregationOperator.$AVG);
    }

    @Test
    public void testSum() throws MalformedJsonQueryException {
        String opString = "{\"$sum\":\"xxxx\"}";
        Aggregation operation = parser.parse(opString);
        assertThat(operation).isInstanceOf(Sum.class);
        assertThat(operation.getOperator()).isEqualTo(AggregationOperator.$SUM);
    }

    @Test
    public void testMax() throws MalformedJsonQueryException {
        String opString = "{\"$max\":\"xxxx\"}";
        Aggregation operation = parser.parse(opString);
        assertThat(operation).isInstanceOf(Max.class);
        assertThat(operation.getOperator()).isEqualTo(AggregationOperator.$MAX);
    }

    @Test
    public void testMin() throws MalformedJsonQueryException {
        String opString = "{\"$min\":\"xxxx\"}";
        Aggregation operation = parser.parse(opString);
        assertThat(operation).isInstanceOf(Min.class);
        assertThat(operation.getOperator()).isEqualTo(AggregationOperator.$MIN);
    }

}
