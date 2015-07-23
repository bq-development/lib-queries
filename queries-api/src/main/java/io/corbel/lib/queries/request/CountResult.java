package io.corbel.lib.queries.request;

/**
 * @author Rubén Carrasco
 *
 */
public class CountResult implements AggregationResult {
	private long count;

	public CountResult() {
	}

	public CountResult(long count) {
		this.count = count;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
