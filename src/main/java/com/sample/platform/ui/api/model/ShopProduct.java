package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShopProduct {
	@JsonProperty("duin")
	private String duin;

	@JsonProperty("title")
	private String title;

	@JsonProperty("contributor")
	private String contributor;

	@JsonProperty("image")
	private String image;

	public String getDuin() {
		return duin;
	}

	public void setDuin(String duin) {
		this.duin = duin;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ShopProduct(String duin, String title, String contributor, String image) {
		this.duin = duin;
		this.title = title;
		this.contributor = contributor;
		this.image = image;
	}

	public ShopProduct() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contributor == null) ? 0 : contributor.hashCode());
		result = prime * result + ((duin == null) ? 0 : duin.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
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
		ShopProduct other = (ShopProduct) obj;
		if (contributor == null) {
			if (other.contributor != null)
				return false;
		} else if (!contributor.equals(other.contributor))
			return false;
		if (duin == null) {
			if (other.duin != null)
				return false;
		} else if (!duin.equals(other.duin))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
