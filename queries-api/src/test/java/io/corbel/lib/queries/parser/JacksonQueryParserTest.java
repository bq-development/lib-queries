package io.corbel.lib.queries.parser;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.QueryLiteral;
import io.corbel.lib.queries.request.QueryNode;
import io.corbel.lib.queries.request.QueryOperator;
import io.corbel.lib.queries.request.ResourceQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Period;
import java.util.Iterator;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class JacksonQueryParserTest {

	private JacksonQueryParser jacksonQueryParser;

	@Before
	public void setup() {
		jacksonQueryParser = createQueryParser();
	}

	@Test
	public void testQueryEquals() throws MalformedJsonQueryException {
		String eqString = "[{\"$eq\":{\"artist\":\"Metallica\"}}]";
		ResourceQuery resourceQuery = jacksonQueryParser.parse(eqString);
		QueryNode node = resourceQuery.iterator().next();

		assertThat(node.getOperator()).isEqualTo(QueryOperator.$EQ);
		assertThat(node.getField()).isEqualTo("artist");
		assertThat((String) node.getValue().getLiteral()).isEqualTo("Metallica");

		eqString = "[{\"artist\":\"Metallica\"}]";
		resourceQuery = jacksonQueryParser.parse(eqString);
		node = resourceQuery.iterator().next();

		assertThat(node.getOperator()).isEqualTo(QueryOperator.$EQ);
		assertThat(node.getField()).isEqualTo("artist");
		assertThat((String) node.getValue().getLiteral()).isEqualTo("Metallica");

		eqString = "[{\"artist\":\"Metallica\"}, {\"album\":\"Master of Puppets\"}]";
		resourceQuery = jacksonQueryParser.parse(eqString);
		Iterator<QueryNode> iterator = resourceQuery.iterator();
		for (int i = 0; i < 2; i++) {
			node = iterator.next();
			assertThat(node.getOperator()).isEqualTo(QueryOperator.$EQ);
			node = resourceQuery.iterator().next();
			switch (node.getField()) {
				case "artist":
					assertThat((String) node.getValue().getLiteral()).isEqualTo("Metallica");
					break;
				case "album":
					assertThat((String) node.getValue().getLiteral()).isEqualTo("Master of Puppets");
					break;
				default:
					fail("node not found");
					break;
			}
		}
	}

	@Test(expected = MalformedJsonQueryException.class)
	public void testTwofieldsInAFilterObject() throws MalformedJsonQueryException {
		String eqString = "[{\"artist\":\"Metallica\", \"album\":\"Master of Puppets\"}]";
		jacksonQueryParser.parse(eqString);
	}

	@Test(expected = MalformedJsonQueryException.class)
	public void testEmptyObject() throws MalformedJsonQueryException {
		String eqString = "[{}]";
		jacksonQueryParser.parse(eqString);
	}

	@Test(expected = MalformedJsonQueryException.class)
	public void testEmptyFilter() throws MalformedJsonQueryException {
		String eqString = "[{\"$eq\":{}},{\"$eq\":{\"artist\":\"ACDC\"}}]";
		jacksonQueryParser.parse(eqString);
	}

	@Test
	public void testDateValue() throws MalformedJsonQueryException {
		String eqString = "[{\"$lt\":{\"date\":\"ISODate(2012-07-14T01:00:00+01:00)\"}}]";
		ResourceQuery resourceQuery = jacksonQueryParser.parse(eqString);
		QueryNode node = resourceQuery.iterator().next();

		assertThat(node.getOperator()).isEqualTo(QueryOperator.$LT);
		assertThat(node.getField()).isEqualTo("date");
		DateTime date = new DateTime(node.getValue().getLiteral());
		assertThat(date.getYear()).isEqualTo(2012);
	}

	@Test
	public void testPeriodValue() throws MalformedJsonQueryException {
		String eqString = "[{\"$lt\":{\"period\":\"Period(P1Y1D)\"}}]";
		ResourceQuery resourceQuery = jacksonQueryParser.parse(eqString);
		QueryNode node = resourceQuery.iterator().next();

		assertThat(node.getOperator()).isEqualTo(QueryOperator.$LT);
		assertThat(node.getField()).isEqualTo("period");
		Period period = Period.parse((String) node.getValue().getLiteral());
		assertThat(period.getYears()).isEqualTo(1);
		assertThat(period.getMonths()).isEqualTo(0);
		assertThat(period.getYears()).isEqualTo(1);
	}

    @Test
    public void testDurationValue() throws MalformedJsonQueryException {
        String eqString = "[{\"$lt\":{\"duration\":\"Period(PT100S)\"}}]";
        ResourceQuery resourceQuery = jacksonQueryParser.parse(eqString);
        QueryNode node = resourceQuery.iterator().next();

        assertThat(node.getOperator()).isEqualTo(QueryOperator.$LT);
        assertThat(node.getField()).isEqualTo("duration");
        Duration duration = Duration.parse((String) node.getValue().getLiteral());
        assertThat(duration.getSeconds()).isEqualTo(100);
    }

	@Test
	public void testArrayField() throws MalformedJsonQueryException {
		String query = "[{\"$in\":{\"artist\":[\"Metallica\",\"ACDC\"]}}]";
		ResourceQuery resourceQuery = jacksonQueryParser.parse(query);
		QueryNode node = resourceQuery.iterator().next();

		assertThat(node.getOperator()).isEqualTo(QueryOperator.$IN);
		assertThat(node.getField()).isEqualTo("artist");
		List<QueryLiteral<String>> list = (List<QueryLiteral<String>>) node.getValue().getLiteral();
		assertThat(list.get(0).getLiteral()).isEqualTo("Metallica");
		assertThat(list.get(1).getLiteral()).isEqualTo("ACDC");
	}

	@Test(expected = MalformedJsonQueryException.class)
	public void testUnsopportedOperationWith() throws MalformedJsonQueryException {
		String eqString = "[{\"$eq\":{\"artist\":[\"Metallica\",\"ACDC\"]}}]";
		ResourceQuery resourceQuery = jacksonQueryParser.parse(eqString);
	}

	@Test(expected = MalformedJsonQueryException.class)
	public void testUnsopportedArrayOperation() throws MalformedJsonQueryException {
		String eqString = "[{\"$all\":{\"artist\":\"Metallica\"]}}]";
		ResourceQuery resourceQuery = jacksonQueryParser.parse(eqString);
	}

	@Test
	public void testEquals() throws MalformedJsonQueryException {
		String query = "[{\"$eq\":{\"artist\":\"Metallica\"}}]";
		ResourceQuery resourceQuery = jacksonQueryParser.parse(query);
		ResourceQuery other = jacksonQueryParser.parse(query);
		assertThat(resourceQuery).isEqualTo(other);
	}

	@Test
	public void testWithLongValue() throws MalformedJsonQueryException {
		String query = "[{\"$gt\":{\"duration\":200}}]";
		ResourceQuery resourceQuery = jacksonQueryParser.parse(query);
		QueryNode node = resourceQuery.iterator().next();

		assertThat(node.getOperator()).isEqualTo(QueryOperator.$GT);
		assertThat(node.getField()).isEqualTo("duration");
		assertThat((Long) node.getValue().getLiteral()).isEqualTo(200l);
	}

	private JacksonQueryParser createQueryParser() {
		return new JacksonQueryParser(new CustomJsonParser(new ObjectMapper().getFactory()));
	}
}
