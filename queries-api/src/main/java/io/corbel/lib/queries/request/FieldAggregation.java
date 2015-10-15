package io.corbel.lib.queries.request;

import java.util.Collections;
import java.util.List;


public abstract class FieldAggregation implements Aggregation {

    protected final String field;

    public FieldAggregation(String field) {
        this.field = field;
    }

    @Override
    public List<ResourceQuery> operate(List<ResourceQuery> resourceQueries) {
        if (resourceQueries == null) {
            resourceQueries = Collections.singletonList(new ResourceQuery());
        }

        return resourceQueries;
    }

    public String getField() {
        return field;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
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
        Count other = (Count) obj;
        if (field == null) {
            if (other.field != null) {
                return false;
            }
        } else if (!field.equals(other.field)) {
            return false;
        }
        return true;
    }

}
