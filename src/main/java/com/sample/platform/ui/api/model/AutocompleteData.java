package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AutocompleteData {
	@JsonProperty("searchedPhrase")
	private String searchedPhrase;
	
	@JsonProperty("inCategory")
	private String inCategory;

	@JsonProperty("inAllCategories")
	private String inAllCategories;

	@JsonProperty("termSuggestions")
	private List<String> termSuggestions;

	@JsonProperty("categoriesSuggestions")
	private List<CategorySearch> categoriesSuggestions;

	public String getSearchedPhrase() {
		return searchedPhrase;
	}

	public void setSearchedPhrase(String searchedPhrase) {
		this.searchedPhrase = searchedPhrase;
	}

	public String getInCategory() {
		return inCategory;
	}

	public void setInCategory(String inCategory) {
		this.inCategory = inCategory;
	}

	public String getInAllCategories() {
		return inAllCategories;
	}

	public void setInAllCategories(String inAllCategories) {
		this.inAllCategories = inAllCategories;
	}

	public List<String> getTermSuggestions() {
		return termSuggestions;
	}

	public void setTermSuggestions(List<String> termSuggestions) {
		this.termSuggestions = termSuggestions;
	}

	public List<CategorySearch> getCategoriesSuggestions() {
		return categoriesSuggestions;
	}

	public void setCategoriesSuggestions(List<CategorySearch> categoriesSuggestions) {
		this.categoriesSuggestions = categoriesSuggestions;
	}

	public AutocompleteData(String searchedPhrase, String inCategory, String inAllCategories,
			List<String> termSuggestions, List<CategorySearch> categoriesSuggestions) {
		this.searchedPhrase = searchedPhrase;
		this.inCategory = inCategory;
		this.inAllCategories = inAllCategories;
		this.termSuggestions = termSuggestions;
		this.categoriesSuggestions = categoriesSuggestions;
	}

	public AutocompleteData() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoriesSuggestions == null) ? 0 : categoriesSuggestions.hashCode());
		result = prime * result + ((inAllCategories == null) ? 0 : inAllCategories.hashCode());
		result = prime * result + ((inCategory == null) ? 0 : inCategory.hashCode());
		result = prime * result + ((searchedPhrase == null) ? 0 : searchedPhrase.hashCode());
		result = prime * result + ((termSuggestions == null) ? 0 : termSuggestions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutocompleteData other = (AutocompleteData) obj;
		if (categoriesSuggestions == null) {
			if (other.categoriesSuggestions != null)
				return false;
		} else if (!categoriesSuggestions.equals(other.categoriesSuggestions))
			return false;
		if (inAllCategories == null) {
			if (other.inAllCategories != null)
				return false;
		} else if (!inAllCategories.equals(other.inAllCategories))
			return false;
		if (inCategory == null) {
			if (other.inCategory != null)
				return false;
		} else if (!inCategory.equals(other.inCategory))
			return false;
		if (searchedPhrase == null) {
			if (other.searchedPhrase != null)
				return false;
		} else if (!searchedPhrase.equals(other.searchedPhrase))
			return false;
		if (termSuggestions == null) {
			if (other.termSuggestions != null)
				return false;
		} else if (!termSuggestions.equals(other.termSuggestions))
			return false;
		return true;
	}
}
