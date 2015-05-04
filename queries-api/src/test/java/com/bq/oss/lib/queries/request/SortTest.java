/*
 * Copyright (C) 2014 StarTIC
 */
package com.bq.oss.lib.queries.request;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Alexander De Leon
 * 
 */
public class SortTest {

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidJson() {
		Sort.fromString("no a json");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotTheExpectedJson() {
		Sort.fromString("[1]");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotMoreThanOneField() {
		Sort.fromString("{\"a\":\"asc\", \"b\":\"desc\"}");
	}

	@Test
	public void testParseAsc() {
		Sort sort = Sort.fromString("{\"a\":\"asc\"}");
		assertThat(sort.getField()).isEqualTo("a");
		assertThat(sort.getDirection()).isEqualTo(Sort.Direction.ASC);
	}

	@Test
	public void testParseDesc() {
		Sort sort = Sort.fromString("{\"a\":\"desc\"}");
		assertThat(sort.getField()).isEqualTo("a");
		assertThat(sort.getDirection()).isEqualTo(Sort.Direction.DESC);
	}
}
