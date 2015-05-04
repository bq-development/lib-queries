package com.bq.oss.lib.queries.request;


public abstract class FieldAggregation implements Aggregation {

	protected final String field;

	public FieldAggregation(String field) {
		this.field = field;
	}

	@Override
	public ResourceQuery operate(ResourceQuery resourceQuery) {
		if (resourceQuery == null) {
			resourceQuery = new ResourceQuery();
		}

		return resourceQuery;
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
