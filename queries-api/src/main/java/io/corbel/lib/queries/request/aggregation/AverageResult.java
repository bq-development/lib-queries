package io.corbel.lib.queries.request.aggregation;

public class AverageResult implements AggregationResult {

	private double average;

	public AverageResult() {
	}

	public AverageResult(double average) {
		super();
		this.average = average;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
}
