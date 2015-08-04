package io.corbel.lib.queries.parser;

import static org.fest.assertions.api.Assertions.assertThat;
import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.Search;

import org.junit.Before;
import org.junit.Test;

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
    public void testValidString() throws MalformedJsonQueryException {
        String searchString = "text";
        Search search = parser.parse(searchString, false);
        assertThat(search.getText().get()).isEqualTo("text");
        assertThat(search.isBinded()).isFalse();
    }

    @Test
    public void testValidObject() throws MalformedJsonQueryException {
        String searchString = "{\"templateName\": \"name\", \"params\": {}}";
        Search search = parser.parse(searchString, true);
        assertThat(search.getTemplate().get()).isEqualTo("name");
        assertThat(search.isBinded()).isTrue();
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testInvalidName() throws MalformedJsonQueryException {
        String searchString = "{\"templateName\": {}, \"params\": {}}";
        parser.parse(searchString, true);
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testInvalidParams() throws MalformedJsonQueryException {
        String searchString = "{\"templateName\": \"name\", \"params\": []}";
        parser.parse(searchString, true);
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testTemplateSerachWithoutName() throws MalformedJsonQueryException {
        String searchString = "{\"params\": {}}";
        parser.parse(searchString, false);
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testTemplateSerachWithoutParams() throws MalformedJsonQueryException {
        String searchString = "{\"templateName\": \"name\"}";
        parser.parse(searchString, false);
    }

}
