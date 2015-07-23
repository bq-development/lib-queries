package io.corbel.lib.queries.request;

/**
 * @author Francisco Sanchez
 * 
 */
public class Pagination {

	private int page;
	private int pageSize;

	public Pagination(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Pagination)) {
			return false;
		}

		Pagination that = (Pagination) o;

		if (pageSize != that.pageSize) {
			return false;
		}
		if (page != that.page) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = page;
		result = 31 * result + pageSize;
		return result;
	}
}
