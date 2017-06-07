package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.Gson;

public class CategoryList {
	private final List<Category> categoryList;

	@JsonValue
	public List<Category> getCategoryList() {
		return categoryList;
	}

	public CategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(categoryList);
	}
}
