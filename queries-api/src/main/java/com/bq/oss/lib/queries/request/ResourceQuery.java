package com.bq.oss.lib.queries.request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

public class ResourceQuery implements Iterable<QueryNode>, Cloneable {

	private final List<QueryNode> conjunctions;

	public ResourceQuery() {
		this(new ArrayList<>());
	}

    public ResourceQuery(List<QueryNode> conjunctions) {
        this.conjunctions = conjunctions;
    }

    public void addQueryNode(QueryNode queryNode) {
		conjunctions.add(queryNode);
	}

	@Override
	public Iterator<QueryNode> iterator() {
		return conjunctions.iterator();
	}

	public Set<String> getFilters() {
		return stream(this.spliterator(), false).map(QueryNode::getField).collect(Collectors.toSet());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + conjunctions.hashCode();
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
		ResourceQuery other = (ResourceQuery) obj;
		return conjunctions.equals(other.conjunctions);
	}

	@Override
	public String toString() {
		return conjunctions.stream().map(this::parseQueryNode).collect(Collectors.joining(",", "[", "]"));
	}

	private String parseQueryNode(QueryNode query) {
		return "{\"" + query.getOperator().name().toLowerCase() + "\":{\"" + query.getField() + "\":"
				+ query.getValue() + "}}";
	}

    @Override
    public ResourceQuery clone(){
        return new ResourceQuery(new ArrayList<>(conjunctions));
    }
}
