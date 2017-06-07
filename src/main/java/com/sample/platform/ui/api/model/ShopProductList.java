package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class ShopProductList {
	private final List<ShopProduct> shopProductList;
	
	@JsonValue
	public List<ShopProduct> getShopProductList() {
		return shopProductList;
	}

	public ShopProductList(List<ShopProduct> shopProductList) {
		this.shopProductList = shopProductList;
	}
}
