package com.github.bajagit.notey.test.projections;

import com.github.bajagit.notey.projections.DocumentProjection;
import com.github.bajagit.notey.projections.NoteyProjection;

@NoteyProjection("view-name")
public class Pojo extends DocumentProjection {
	
	private String val;
	
	public String getVal() {
		return val;
	}
}