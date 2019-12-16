package com.awcsoftware.app.common;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ParentBasePojo {

	private int id;
	private String name;
	private List<BasePojo> basepojo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BasePojo> getBasepojo() {
		return basepojo;
	}

	public void setBasepojo(List<BasePojo> basepojo) {
		this.basepojo = basepojo;
	}

}
