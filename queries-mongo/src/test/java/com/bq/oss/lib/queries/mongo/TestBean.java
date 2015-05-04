/*
 * Copyright (C) 2014 StarTIC
 */
package com.bq.oss.lib.queries.mongo;

import org.springframework.data.annotation.Id;

/**
 * @author Alexander De Leon
 */
public class TestBean {

	@Id
	public String id;

	public String a;

	public Integer b;

	public String c;

	public TestBean embeded;
}