package io.corbel.lib.queries.mongo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;

import io.corbel.lib.mongo.repository.impl.ExtendedRepository;
import io.corbel.lib.queries.mongo.builder.MongoQueryBuilder;
import io.corbel.lib.queries.request.*;

/**
 * @author Rubén Carrasco
 * 
 */
public class FindExtendedRepository<E, ID extends Serializable> extends ExtendedRepository<E, ID>implements GenericFindRepository<E, ID> {

    private final MongoOperations mongoOperations;
    private final MongoEntityInformation<E, ID> metadata;

    /**
     * @param metadata
     * @param mongoOperations
     */
    public FindExtendedRepository(MongoEntityInformation<E, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
        this.metadata = metadata;
    }

    @Override
    public List<E> find(ResourceQuery resourceQuery, Pagination pagination, Sort sort) {
        Query query = new MongoQueryBuilder().query(resourceQuery).pagination(pagination).sort(sort).build();
        return mongoOperations.find(query, metadata.getJavaType(), metadata.getCollectionName());
    }

    @Override
    public List<E> find(List<ResourceQuery> resourceQueries, Pagination pagination, Sort sort) {
        Query query = new MongoQueryBuilder().query(resourceQueries).pagination(pagination).sort(sort).build();
        return mongoOperations.find(query, metadata.getJavaType(), metadata.getCollectionName());
    }

    @Override
    public AggregationResult count(ResourceQuery resourceQuery) {
        Query query = new MongoQueryBuilder().query(resourceQuery).build();
        return new CountResult(mongoOperations.count(query, metadata.getCollectionName()));
    }
}
