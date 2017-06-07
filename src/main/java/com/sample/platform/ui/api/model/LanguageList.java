package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.Gson;

public class LanguageList {
	private final List<Language> languageList;
	
	@JsonValue
	public List<Language> getLanguageList() {
		return languageList;
	}

	public LanguageList(List<Language> languageList) {
		this.languageList = languageList;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(languageList);
	}
}
