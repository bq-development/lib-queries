package io.corbel.lib.queries.parser;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Optional;

import io.corbel.lib.queries.request.Search;
import org.junit.Before;
import org.junit.Test;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Rubén Carrasco
 *
 */
public class CustomSearchParserTest {

    CustomSearchParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new CustomSearchParser(new ObjectMapper());
    }

    @Test
    public void testValidObject() throws MalformedJsonQueryException {
        String searchString = "{\"text\": \"text\", \"fields\": [\"field1\", \"field2\", \"field2\"], \"binded\": true}";
        Search search = parser.parse(searchString);
        assertThat(search.getText()).isEqualTo("text");
        assertThat(search.isBinded()).isTrue();
        assertThat(search.getFields().get()).isNotEmpty();
        assertThat(search.getFields().get().size()).isEqualTo(2);
        assertThat(search.getFields().get().contains("field1")).isTrue();
        assertThat(search.getFields().get().contains("field2")).isTrue();
    }

    @Test
    public void testValidObjectWithoutOptional() throws MalformedJsonQueryException {
        String searchString = "{\"text\": \"text\"}";
        Search search = parser.parse(searchString);
        assertThat(search.getText()).isEqualTo("text");
        assertThat(search.isBinded()).isFalse();
        assertThat(search.getFields()).isEqualTo(Optional.empty());
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testNotValidText() throws MalformedJsonQueryException {
        String searchString = "{\"text\": {}}";
        parser.parse(searchString);
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testNotValidFields() throws MalformedJsonQueryException {
        String searchString = "{\"text\": \"text\", \"fields\": [\"field1\", {}, \"field2\"], \"binded\": true}";
        parser.parse(searchString);
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testNotValidBinded() throws MalformedJsonQueryException {
        String searchString = "{\"text\": \"text\", \"fields\": [\"field1\", \"field2\", \"field2\"], \"binded\": \"asdf\"}";
        parser.parse(searchString);
    }

}
