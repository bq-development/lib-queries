package com.bq.oss.lib.queries.matcher;

import static org.fest.assertions.api.Assertions.assertThat;

import com.bq.oss.lib.queries.LongQueryLiteral;
import com.bq.oss.lib.queries.QueryNodeImpl;
import com.bq.oss.lib.queries.request.QueryNode;
import com.bq.oss.lib.queries.request.QueryOperator;
import com.bq.oss.lib.queries.request.ResourceQuery;
import org.junit.Before;
import org.junit.Test;

import com.bq.oss.lib.queries.StringQueryLiteral;
import com.bq.oss.lib.queries.exception.QueryMatchingException;

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
		private String myStringField;
		private String myOtherStringField;
		private long longField;
		private int intField;
		private double doubleField;

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
