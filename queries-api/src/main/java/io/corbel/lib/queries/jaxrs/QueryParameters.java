package io.corbel.lib.queries.jaxrs;

import io.corbel.lib.queries.request.Aggregation;
import io.corbel.lib.queries.request.Pagination;
import io.corbel.lib.queries.request.ResourceQuery;
import io.corbel.lib.queries.request.Search;
import io.corbel.lib.queries.request.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * @author Alexander De Leon
 */
public class QueryParameters {

    private Pagination pagination;
    private Optional<Sort> sort;
    private Optional<List<ResourceQuery>> queries;
    private Optional<List<ResourceQuery>> conditions;
    private Optional<Aggregation> aggreagation;
    private Optional<Search> search;

    public QueryParameters(Pagination pagination, Optional<Sort> sort, Optional<List<ResourceQuery>> queries,
            Optional<List<ResourceQuery>> conditions, Optional<Aggregation> aggreagation, Optional<Search> search) {
        this.pagination = pagination;
        this.sort = sort;
        this.queries = queries;
        this.conditions = conditions;
        this.aggreagation = aggreagation;
        this.search = search;
    }

    public QueryParameters(QueryParameters other) {
        this.pagination = other.pagination;
        this.sort = other.sort;
        this.queries = other.queries;
        this.conditions = other.conditions;
        this.aggreagation = other.aggreagation;
        this.search = other.search;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Optional<Sort> getSort() {
        return sort;
    }

    public void setSort(Optional<Sort> sort) {
        this.sort = sort;
    }

    @Deprecated
    public Optional<ResourceQuery> getQuery() {
        return Optional.ofNullable(queries.map(queries -> queries.get(0)).orElse(null));
    }

    public void setQuery(Optional<ResourceQuery> optionalQuery) {
        queries = Optional.ofNullable(optionalQuery.map(query -> Arrays.asList(query)).orElse(null));
    }

    public Optional<List<ResourceQuery>> getQueries() {
        return queries;
    }

    public void setQueries(Optional<List<ResourceQuery>> listQueries) {
        this.queries = listQueries;
    }

    public Optional<List<ResourceQuery>> getConditions() {
        return conditions;
    }

    public void setConditions(Optional<List<ResourceQuery>> conditions) {
        this.conditions = conditions;
    }

    public Optional<Search> getSearch() {
        return search;
    }

    public void setSearch(Optional<Search> search) {
        this.search = search;
    }

    public Optional<Aggregation> getAggregation() {
        return aggreagation;
    }

    public void setAggregation(Optional<Aggregation> aggregationOperation) {
        this.aggreagation = aggregationOperation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aggreagation == null) ? 0 : aggreagation.hashCode());
        result = prime * result + ((conditions == null) ? 0 : conditions.hashCode());
        result = prime * result + ((pagination == null) ? 0 : pagination.hashCode());
        result = prime * result + ((queries == null) ? 0 : queries.hashCode());
        result = prime * result + ((search == null) ? 0 : search.hashCode());
        result = prime * result + ((sort == null) ? 0 : sort.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        QueryParameters other = (QueryParameters) obj;
        if (aggreagation == null) {
            if (other.aggreagation != null) {
                return false;
            }
        } else if (!aggreagation.equals(other.aggreagation)) {
            return false;
        }
        if (conditions == null) {
            if (other.conditions != null) {
                return false;
            }
        } else if (!conditions.equals(other.conditions)) {
            return false;
        }
        if (pagination == null) {
            if (other.pagination != null) {
                return false;
            }
        } else if (!pagination.equals(other.pagination)) {
            return false;
        }
        if (queries == null) {
            if (other.queries != null) {
                return false;
            }
        } else if (!queries.equals(other.queries)) {
            return false;
        }
        if (search == null) {
            if (other.search != null) {
                return false;
            }
        } else if (!search.equals(other.search)) {
            return false;
        }
        if (sort == null) {
            if (other.sort != null) {
                return false;
            }
        } else if (!sort.equals(other.sort)) {
            return false;
        }
        return true;
    }



}
