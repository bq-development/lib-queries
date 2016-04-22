package io.corbel.lib.queries.matcher;

import io.corbel.lib.queries.ListQueryLiteral;
import io.corbel.lib.queries.LongQueryLiteral;
import io.corbel.lib.queries.QueryNodeImpl;
import io.corbel.lib.queries.StringQueryLiteral;
import io.corbel.lib.queries.exception.QueryMatchingException;
import io.corbel.lib.queries.request.QueryNode;
import io.corbel.lib.queries.request.QueryOperator;
import io.corbel.lib.queries.request.ResourceQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class QueryMatcherTest {

    private QueryMatcher queryMatcher;

    @Before
    public void before() {
        queryMatcher = new DefaultQueryMatcher();
    }

    @Test
    public void matchObjectEmptyQueryTest() throws QueryMatchingException {
        assertThat(queryMatcher.matchObject(new ResourceQuery(), new Object())).isTrue();
    }

    @Test
    public void matchObjectStringTest() throws QueryMatchingException {
        QueryNode queryNode1 = new QueryNodeImpl(QueryOperator.$EQ, "myStringField",
                new StringQueryLiteral("something"));
        QueryNode queryNode2 = new QueryNodeImpl(QueryOperator.$LIKE, "myOtherStringField", new StringQueryLiteral(
                ".*something.*"));

        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.addQueryNode(queryNode1);
        resourceQuery.addQueryNode(queryNode2);
        TestClass testObject = new TestClass();
        testObject.setMyStringField("something");
        testObject.setMyOtherStringField("some something");
        assertThat(queryMatcher.matchObject(resourceQuery, testObject)).isTrue();

        ResourceQuery resourceQuery2 = new ResourceQuery();
        resourceQuery2.addQueryNode(queryNode2);
        resourceQuery2.addQueryNode(queryNode1);
        TestClass testObject2 = new TestClass();
        testObject2.setMyStringField("other");
        testObject2.setMyOtherStringField("some something");
        assertThat(queryMatcher.matchObject(resourceQuery2, testObject2)).isFalse();
    }


    @Test
    public void matchObjectLikeListStringTest() throws QueryMatchingException {
        QueryNode queryNode = new QueryNodeImpl(QueryOperator.$LIKE, "listField", new StringQueryLiteral(
                ".*AB"));

        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.addQueryNode(queryNode);

        TestClass testObject = new TestClass();
        testObject.setListField(Arrays.asList("AB", "CDAB"));
        testObject.setMyOtherStringField("some something");
        assertThat(queryMatcher.matchObject(resourceQuery, testObject)).isTrue();
    }

    @Test
    public void matchObjectWithEmptyINTest() throws QueryMatchingException {
        QueryNode queryNode = new QueryNodeImpl(QueryOperator.$IN, "listField", new ListQueryLiteral());

        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.addQueryNode(queryNode);

        TestClass testObject = new TestClass();
        testObject.setListField(Arrays.asList("AB", "CDAB"));
        testObject.setMyOtherStringField("some something");
        assertThat(queryMatcher.matchObject(resourceQuery, testObject)).isFalse();
    }

    @Test
    public void matchLongSizeTest() throws QueryMatchingException {
        List list = new LinkedList<String>();
        QueryNode queryNode = new QueryNodeImpl(QueryOperator.$SIZE, "listField", new LongQueryLiteral(0l));
        TestClass testObject = new TestClass();
        testObject.setListField(list);

        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.addQueryNode(queryNode);

        assertThat(queryMatcher.matchObject(resourceQuery, testObject)).isTrue();

        list.add("one");
        list.add("two");
        QueryNode queryNode2 = new QueryNodeImpl(QueryOperator.$SIZE, "listField", new LongQueryLiteral(2l));
        ResourceQuery resourceQuery2 = new ResourceQuery();
        resourceQuery2.addQueryNode(queryNode2);
        assertThat(queryMatcher.matchObject(resourceQuery2, testObject)).isTrue();

    }

    @Test
    public void matchLongTest() throws QueryMatchingException {
        QueryNode queryNode1 = new QueryNodeImpl(QueryOperator.$EQ, "longField", new LongQueryLiteral(123l));
        QueryNode queryNode2 = new QueryNodeImpl(QueryOperator.$LT, "intField", new LongQueryLiteral(123l));
        QueryNode queryNode3 = new QueryNodeImpl(QueryOperator.$EQ, "doubleField", new LongQueryLiteral(123l));
        QueryNode queryNode4 = new QueryNodeImpl(QueryOperator.$EQ, "myStringField",
                new StringQueryLiteral("something"));

        TestClass testObject = new TestClass();
        testObject.setLongField(123);
        testObject.setIntField(122);
        testObject.setDoubleField(123);
        testObject.setMyStringField("something");

        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.addQueryNode(queryNode1);
        resourceQuery.addQueryNode(queryNode2);
        resourceQuery.addQueryNode(queryNode3);
        resourceQuery.addQueryNode(queryNode4);
        assertThat(queryMatcher.matchObject(resourceQuery, testObject)).isTrue();
    }

    @Test(expected = QueryMatchingException.class)
    public void accessToNotExistingPropertyTest() throws QueryMatchingException {
        QueryNode queryNode1 = new QueryNodeImpl(QueryOperator.$EQ, "notExistingField", new StringQueryLiteral(
                "something"));
        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.addQueryNode(queryNode1);

        queryMatcher.matchObject(resourceQuery, new TestClass());
    }

    public class TestClass {
        private List<Object> listField;
        private String myStringField;
        private String myOtherStringField;
        private long longField;
        private int intField;
        private double doubleField;

        public List<Object> getListField() {
            return listField;
        }

        public void setListField(List<Object> listField) {
            this.listField = listField;
        }

        public String getMyStringField() {
            return myStringField;
        }

        public void setMyStringField(String myStringField) {
            this.myStringField = myStringField;
        }

        public String getMyOtherStringField() {
            return myOtherStringField;
        }

        public void setMyOtherStringField(String myOtherStringField) {
            this.myOtherStringField = myOtherStringField;
        }

        public long getLongField() {
            return longField;
        }

        public void setLongField(long longField) {
            this.longField = longField;
        }

        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }

        public double getDoubleField() {
            return doubleField;
        }

        public void setDoubleField(double doubleField) {
            this.doubleField = doubleField;
        }
    }
}
