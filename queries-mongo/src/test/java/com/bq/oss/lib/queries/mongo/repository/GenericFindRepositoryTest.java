/*
 * Copyright (C) 2014 StarTIC
 */
package com.bq.oss.lib.queries.mongo.repository;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.bq.oss.lib.queries.QueryNodeImpl;
import com.bq.oss.lib.queries.StringQueryLiteral;
import com.bq.oss.lib.queries.mongo.TestBean;
import com.bq.oss.lib.queries.request.Pagination;
import com.bq.oss.lib.queries.request.QueryOperator;
import com.bq.oss.lib.queries.request.ResourceQuery;
import com.bq.oss.lib.queries.request.Sort;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * @author Alexander De Leon
 * 
 */
public class GenericFindRepositoryTest {

	private static final String TEST_COLLECTION = "collection";

	private FindExtendedRepository<TestBean, String> repo;
	private MongoEntityInformation<TestBean, String> metadata;
	private MongoOperations mongoOperations;
	private MongoConverter mongoConverter;

	@Before
	public void setup() {
		metadata = mock(MongoEntityInformation.class);
		when(metadata.getCollectionName()).thenReturn(TEST_COLLECTION);
		when(metadata.getJavaType()).thenReturn(TestBean.class);
		when(metadata.getIdType()).thenReturn(String.class);
		mongoOperations = mock(MongoOperations.class);
		mongoConverter = mock(MongoConverter.class);
		when(mongoOperations.getConverter()).thenReturn(mongoConverter);
		repo = new FindExtendedRepository<TestBean, String>(metadata, mongoOperations);
	}

	@Test
	public void testFind() {
		ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);

		StringQueryLiteral literal = new StringQueryLiteral();
		literal.setLiteral("Metallica");
		QueryNodeImpl queryNodeImpl = new QueryNodeImpl(QueryOperator.$LIKE, "name", literal);
		ResourceQuery resourceQuery = new ResourceQuery();
		resourceQuery.addQueryNode(queryNodeImpl);

		literal = new StringQueryLiteral();
		literal.setLiteral("Master of puppets");
		queryNodeImpl = new QueryNodeImpl(QueryOperator.$EQ, "Title", literal);
		resourceQuery.addQueryNode(queryNodeImpl);

		when(mongoOperations.find(queryCaptor.capture(), Mockito.eq(TestBean.class), Mockito.eq(TEST_COLLECTION)))
				.thenReturn(new ArrayList<>());

		List<TestBean> result = repo.find(resourceQuery, new Pagination(10, 100), new Sort(Direction.ASC.toString(),
				"name"));

		BasicDBList dbList = (BasicDBList) queryCaptor.getValue().getQueryObject().get("$and");
		assertThat(((DBObject) dbList.get(0)).get("name").toString()).isEqualTo("Metallica");
		assertThat(((DBObject) dbList.get(1)).get("Title").toString()).isEqualTo("Master of puppets");
		assertThat(dbList.size()).isEqualTo(2);
	}
}
