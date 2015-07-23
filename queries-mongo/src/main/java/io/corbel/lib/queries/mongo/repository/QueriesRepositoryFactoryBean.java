package io.corbel.lib.queries.mongo.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

/**
 * @author Rubén Carrasco
 * 
 */
public class QueriesRepositoryFactoryBean<R extends MongoRepository<T, I>, T, I extends Serializable> extends
		MongoRepositoryFactoryBean<R, T, I> {

	@Override
	protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
		return new MongoCommonRepositoryFactory(operations);
	}

	private class MongoCommonRepositoryFactory extends MongoRepositoryFactory {

		private final MongoOperations mongoOperations;

		public MongoCommonRepositoryFactory(MongoOperations mongoOperations) {
			super(mongoOperations);
			this.mongoOperations = mongoOperations;
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return FindExtendedRepository.class;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected Object getTargetRepository(RepositoryMetadata metadata) {
			return new FindExtendedRepository(getEntityInformation(metadata.getDomainType()), mongoOperations);
		}
	}

}
