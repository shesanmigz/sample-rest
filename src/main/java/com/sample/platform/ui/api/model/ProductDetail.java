package com.sample.platform.ui.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDetail {
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("subtitle")
	private String subtitle;
	
	@JsonProperty("categories")
	private List<Breadcrumb> categories = new ArrayList<Breadcrumb>();
	
	@JsonProperty("media")
	private List<Media> media = new ArrayList<Media>();
	
	@JsonProperty("hidden")
	private Boolean hidden;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	public List<Breadcrumb> getCategories() {
		return categories;
	}
	
	public void setCategories(List<Breadcrumb> categories) {
		this.categories = categories;
	}
	
	public List<Media> getMedia() {
		return media;
	}
	
	public void setMedia(List<Media> media) {
		this.media = media;
	}
	
	public Boolean getHidden() {
		return hidden;
	}
	
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	
	public ProductDetail(String title, String subtitle, List<Breadcrumb> categories, List<Media> media, Boolean hidden) {
		this.title = title;
		this.subtitle = subtitle;
		this.categories = categories;
		this.media = media;
		this.hidden = hidden;
	}
	
	public ProductDetail() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((hidden == null) ? 0 : hidden.hashCode());
		result = prime * result + ((media == null) ? 0 : media.hashCode());
		result = prime * result + ((subtitle == null) ? 0 : subtitle.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		ProductDetail other = (ProductDetail) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (hidden == null) {
			if (other.hidden != null)
				return false;
		} else if (!hidden.equals(other.hidden))
			return false;
		if (media == null) {
			if (other.media != null)
				return false;
		} else if (!media.equals(other.media))
			return false;
		if (subtitle == null) {
			if (other.subtitle != null)
				return false;
		} else if (!subtitle.equals(other.subtitle))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
