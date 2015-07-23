package io.corbel.lib.queries.mongo.repository;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;

import io.corbel.lib.queries.QueryNodeImpl;
import io.corbel.lib.queries.StringQueryLiteral;
import io.corbel.lib.queries.mongo.TestBean;
import io.corbel.lib.queries.request.Pagination;
import io.corbel.lib.queries.request.QueryOperator;
import io.corbel.lib.queries.request.ResourceQuery;
import io.corbel.lib.queries.request.Sort;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Alexander De Leon
 * 
 */
public class GenericFindRepositoryTest {

	private static final String $OR = "$or";

	private static final String $AND = "$and";

	private static final String TITLE = "Title";

	private static final String NAME = "name";

	private static final String KILL_EM_ALL = "Kill'em All";

	private static final String MASTER_OF_PUPPETS = "Master of puppets";

	private static final String METALLICA = "Metallica";

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

		ResourceQuery resourceQuery = getResourceQuery(METALLICA, MASTER_OF_PUPPETS);

		when(mongoOperations.find(queryCaptor.capture(), Mockito.eq(TestBean.class), Mockito.eq(TEST_COLLECTION)))
				.thenReturn(new ArrayList<>());

		List<TestBean> result = repo.find(resourceQuery, new Pagination(10, 100), new Sort(Direction.ASC.toString(),
				NAME));

		BasicDBList dbList = (BasicDBList) queryCaptor.getValue().getQueryObject().get($AND);
		assertThat(((DBObject) dbList.get(0)).get(NAME).toString()).isEqualTo(METALLICA);
		assertThat(((DBObject) dbList.get(1)).get(TITLE).toString()).isEqualTo(MASTER_OF_PUPPETS);
		assertThat(dbList.size()).isEqualTo(2);
	}

	@Test
	public void testFindOr() {
		ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);

		List<ResourceQuery> resourceQueries = new ArrayList<>();
		resourceQueries.add(getResourceQuery(METALLICA, MASTER_OF_PUPPETS));
		resourceQueries.add(getResourceQuery(METALLICA, KILL_EM_ALL));

		when(mongoOperations.find(queryCaptor.capture(), Mockito.eq(TestBean.class), Mockito.eq(TEST_COLLECTION)))
				.thenReturn(new ArrayList<>());

		List<TestBean> result = repo.find(resourceQueries, new Pagination(10, 100), new Sort(Direction.ASC.toString(),
				NAME));

		BasicDBList dbOrList = (BasicDBList) queryCaptor.getValue().getQueryObject().get($OR);

		BasicDBList dbList = (BasicDBList) ((BasicDBObject) dbOrList.get(0)).get($AND);
		assertThat(((DBObject) dbList.get(0)).get(NAME).toString()).isEqualTo(METALLICA);
		assertThat(((DBObject) dbList.get(1)).get(TITLE).toString()).isEqualTo(MASTER_OF_PUPPETS);
		assertThat(dbList.size()).isEqualTo(2);

		dbList = (BasicDBList) ((BasicDBObject) dbOrList.get(1)).get($AND);
		assertThat(((DBObject) dbList.get(0)).get(NAME).toString()).isEqualTo(METALLICA);
		assertThat(((DBObject) dbList.get(1)).get(TITLE).toString()).isEqualTo(KILL_EM_ALL);
		assertThat(dbList.size()).isEqualTo(2);
	}

	private ResourceQuery getResourceQuery(String name, String title) {
		StringQueryLiteral literal = new StringQueryLiteral();
		literal.setLiteral(name);
		QueryNodeImpl queryNodeImpl = new QueryNodeImpl(QueryOperator.$LIKE, NAME, literal);
		ResourceQuery resourceQuery = new ResourceQuery();
		resourceQuery.addQueryNode(queryNodeImpl);

		literal = new StringQueryLiteral();
		literal.setLiteral(title);
		queryNodeImpl = new QueryNodeImpl(QueryOperator.$EQ, TITLE, literal);
		resourceQuery.addQueryNode(queryNodeImpl);
		return resourceQuery;
	}
}
