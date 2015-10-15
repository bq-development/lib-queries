package io.corbel.lib.queries.mongo.builder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Query;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.parser.CustomJsonParser;
import io.corbel.lib.queries.parser.JacksonQueryParser;
import io.corbel.lib.queries.request.ResourceQuery;

public class MongoQueryBuilderTest {

    private static JacksonQueryParser parser;

    @BeforeClass
    public static void setUp() throws MalformedJsonQueryException {
        parser = new JacksonQueryParser(new CustomJsonParser(new ObjectMapper().getFactory()));
    }

    @Test
    public void testBuildWithInResourceQuery() throws MalformedJsonQueryException {
        ResourceQuery resourceQuery = parser.parse("[{\"$in\":{\"categories\":[\"Metallica\"]}}]");
        Query query = new MongoQueryBuilder().query(resourceQuery).build();
        assertEquals("{\"$in\":[\"Metallica\"]}", query.getQueryObject().toMap().get("categories").toString().replace(" ", ""));
    }

    @Test
    public void testBuildWithNinResourceQuery() throws MalformedJsonQueryException {
        ResourceQuery resourceQuery = parser.parse("[{\"$nin\":{\"categories\":[\"Metallica\"]}}]");
        Query query = new MongoQueryBuilder().query(resourceQuery).build();
        assertEquals("{\"$nin\":[\"Metallica\"]}", query.getQueryObject().toMap().get("categories").toString().replace(" ", ""));
    }

    @Test
    public void buildQueriesTest() throws MalformedJsonQueryException {
        List<ResourceQuery> resourceQueries = new ArrayList<ResourceQuery>();
        resourceQueries.add(parser.parse("[{\"$lte\":{\"duration\":238.0}},{\"$gte\":{\"duration\":238.0}}]"));
        resourceQueries.add(parser.parse("[{\"$lte\":{\"duration\":245.0}},{\"$gte\":{\"duration\":245.0}}]"));
        Query query = new MongoQueryBuilder().query(resourceQueries).build();
        assertEquals(
                "{\"$or\":[{\"$and\":[{\"duration\":{\"$lte\":238.0}},{\"duration\":{\"$gte\":238.0}}]},{\"$and\":[{\"duration\":{\"$lte\":245.0}},{\"duration\":{\"$gte\":245.0}}]}]}",
                query.getQueryObject().toString().replace(" ", ""));
    }

    @Test
    public void buildQueriesWithOneResourceQueryTest() throws MalformedJsonQueryException {
        List<ResourceQuery> resourceQueries = new ArrayList<ResourceQuery>();
        resourceQueries.add(parser.parse("[{\"$lte\":{\"duration\":238.0}},{\"$gte\":{\"duration\":238.0}}]"));
        Query query = new MongoQueryBuilder().query(resourceQueries).build();
        assertEquals("{\"$and\":[{\"duration\":{\"$lte\":238.0}},{\"duration\":{\"$gte\":238.0}}]}",
                query.getQueryObject().toString().replace(" ", ""));
    }

    @Test
    public void repeatFieldTest() throws MalformedJsonQueryException {
        ResourceQuery resourceQuery = parser.parse("[{\"$lte\":{\"duration\":238.0}},{\"$gte\":{\"duration\":238.0}}]");
        Query query = new MongoQueryBuilder().query(resourceQuery).build();
        assertEquals("{\"$and\":[{\"duration\":{\"$lte\":238.0}},{\"duration\":{\"$gte\":238.0}}]}",
                query.getQueryObject().toString().replace(" ", ""));
    }

    @Test
    public void likeQueryTest() throws MalformedJsonQueryException {
        ResourceQuery resourceQuery = parser.parse("[{\"$like\":{\"name\":\"Metallica\"}}]");
        Query query = new MongoQueryBuilder().query(resourceQuery).build();
        assertEquals("{\"name\":{\"$regex\":\"Metallica\",\"$options\":\"i\"}}", query.getQueryObject().toString().replace(" ", ""));
    }

    @Test
    public void eqQueryTest() throws MalformedJsonQueryException {
        Query query = new MongoQueryBuilder().query(parser.parse("[{\"$eq\":{\"name\":\"Metallica\"}}]")).build();
        Query query2 = new MongoQueryBuilder().query(parser.parse("[{\"name\":\"Metallica\"}]")).build();
        assertEquals(query.getQueryObject().toString(), query2.getQueryObject().toString());
    }

    @Test
    public void elemMatchQueryTest() throws MalformedJsonQueryException {
        String queryString = "[{\"$elem_match\":{\"group\":[{\"year\":\"1992\"},{\"$like\":{\"name\":\"Metallica\"}}]}}]";
        ResourceQuery resourceQuery = parser.parse(queryString);
        Query query = new MongoQueryBuilder().query(resourceQuery).build();
        assertEquals(
                "{\"group\":{\"$elemMatch\":{\"$and\":[{\"year\":\"1992\"},{\"name\":{\"$regex\":\"Metallica\",\"$options\":\"i\"}}]}}}",
                query.getQueryObject().toString().replace(" ", ""));
    }

    @Test
    public void elemMatchQuerySameOpTest() throws MalformedJsonQueryException {
        String queryString = "[{\"$elem_match\":{\"track\":[{\"$lte\":{\"duration\":238.0}},{\"$gte\":{\"duration\":238.0}}]}}]";
        ResourceQuery resourceQuery = parser.parse(queryString);
        Query query = new MongoQueryBuilder().query(resourceQuery).build();
        assertEquals("{\"track\":{\"$elemMatch\":{\"$and\":[{\"duration\":{\"$lte\":238.0}},{\"duration\":{\"$gte\":238.0}}]}}}",
                query.getQueryObject().toString().replace(" ", ""));
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void malformedElemMatchQueryTest() throws MalformedJsonQueryException {
        parser.parse("[{\"$elem_match\":{\"name\":\"Metallica\"}}]");
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testMalformedOperator() throws MalformedJsonQueryException {
        parser.parse("[{\"$lasdf\":{\"name\":\"Metallica\"}}]");
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void testMalformedQuery() throws MalformedJsonQueryException {
        parser.parse("[{\"error\":{\"name\":\"Metallica\"}}]");
    }

    @Test(expected = MalformedJsonQueryException.class)
    public void elemMatchNoOpQueryTest() throws MalformedJsonQueryException {
        parser.parse("[{\"$elem_match\":{\"items\":{\"productId\":\"testproduct01\"}}}]");
    }

    @Test
    public void testEmptyResourceQuery() throws MalformedJsonQueryException {
        String queryString = "[]";
        ResourceQuery resourceQuery = parser.parse(queryString);
        Query query = new MongoQueryBuilder().query(resourceQuery).build();
        assertEquals("{}", query.getQueryObject().toString().replace(" ", ""));
    }

    @Test
    public void testNullResourceQuery() throws MalformedJsonQueryException {
        ResourceQuery resourceQuery = null;
        Query query = new MongoQueryBuilder().query(resourceQuery).build();
        assertEquals("{}", query.getQueryObject().toString().replace(" ", ""));
    }

}
