package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.Gson;

public class CountryList {
	private final List<Country> countryList;
	
	@JsonValue
	public List<Country> getCountryList() {
		return countryList;
	}

	public CountryList(List<Country> countryList) {
		this.countryList = countryList;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(countryList);
	}
}
