package com.bq.oss.lib.queries.request;

/**
 * @author Francisco Sanchez
 */
public class ResourceSearch {
	private String search;

	public ResourceSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ResourceSearch))
			return false;

		ResourceSearch that = (ResourceSearch) o;

		if (search != null ? !search.equals(that.search) : that.search != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return search != null ? search.hashCode() : 0;
	}
}
