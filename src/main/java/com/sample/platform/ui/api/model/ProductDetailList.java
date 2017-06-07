package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.Gson;

public class ProductDetailList {
	private final List<ProductDetail> productDetailList;
	
	@JsonValue
	public List<ProductDetail> getProductDetailList() {
		return productDetailList;
	}

	public ProductDetailList(List<ProductDetail> productDetailList) {
		this.productDetailList = productDetailList;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(productDetailList);
	}
}
