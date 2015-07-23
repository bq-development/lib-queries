package io.corbel.lib.queries.request;

public class SumResult implements AggregationResult {

	private double sum;

	public SumResult() {
	}

	public SumResult(double sum) {
		super();
		this.sum = sum;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}
}
