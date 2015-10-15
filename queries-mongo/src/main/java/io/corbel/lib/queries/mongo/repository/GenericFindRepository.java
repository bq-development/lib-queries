package io.corbel.lib.queries.mongo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.corbel.lib.queries.request.AggregationResult;
import io.corbel.lib.queries.request.Pagination;
import io.corbel.lib.queries.request.ResourceQuery;
import io.corbel.lib.queries.request.Sort;

/**
 * @author Rubén Carrasco
 *
 */
public interface GenericFindRepository<E, ID extends Serializable> extends MongoRepository<E, ID> {

    /**
     * Returns a List of E Objects that match the assertions contained in the specified ResourdceQuery. The result can be paginated or/and
     * sorted.
     *
     * @param resourceQuery Object with the assertions for finding
     * @param pagination Object to paginate the result of the search
     * @param sort Object to sort the result
     * @return List of E Objects
     */
    List<E> find(ResourceQuery resourceQuery, Pagination pagination, Sort sort);

    /**
     * Returns a List of E Objects that match the disjunction of the assertions contained in ResourdceQueries. The result can be paginated
     * or/and sorted.
     *
     * @param resourceQueries Object with the assertions for finding
     * @param pagination Object to paginate the result of the search
     * @param sort Object to sort the result
     * @return List of E Objects
     */
    List<E> find(List<ResourceQuery> resourceQueries, Pagination pagination, Sort sort);

    /**
     * Returns the number of E Objects that match the assertions contained in the specified ResourdceQuery.
     *
     * @param resourceQuery Object with the assertions for finding
     * @return the number of E Objects that match the query
     */
    AggregationResult count(ResourceQuery resourceQuery);

}
