package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Promotion {
	@JsonProperty("imageLarge")
	private String imageLarge;

	@JsonProperty("imageSmall")
	private String imageSmall;

	@JsonProperty("imageMedium")
	private String imageMedium;

	@JsonProperty("url")
	private String url;

	@JsonProperty("texts")
	private List<Text> texts;

	public String getImageLarge() {
		return imageLarge;
	}

	public void setImageLarge(String imageLarge) {
		this.imageLarge = imageLarge;
	}

	public String getImageSmall() {
		return imageSmall;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public String getImageMedium() {
		return imageMedium;
	}

	public void setImageMedium(String imageMedium) {
		this.imageMedium = imageMedium;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Text> getTexts() {
		return texts;
	}

	public void setTexts(List<Text> texts) {
		this.texts = texts;
	}

	public Promotion(String imageLarge, String imageSmall, String imageMedium, String url,
			List<Text> texts) {
		this.imageLarge = imageLarge;
		this.imageSmall = imageSmall;
		this.imageMedium = imageMedium;
		this.url = url;
		this.texts = texts;
	}

	public Promotion() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imageLarge == null) ? 0 : imageLarge.hashCode());
		result = prime * result + ((imageMedium == null) ? 0 : imageMedium.hashCode());
		result = prime * result + ((imageSmall == null) ? 0 : imageSmall.hashCode());
		result = prime * result + ((texts == null) ? 0 : texts.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Promotion other = (Promotion) obj;
		if (imageLarge == null) {
			if (other.imageLarge != null)
				return false;
		} else if (!imageLarge.equals(other.imageLarge))
			return false;
		if (imageMedium == null) {
			if (other.imageMedium != null)
				return false;
		} else if (!imageMedium.equals(other.imageMedium))
			return false;
		if (imageSmall == null) {
			if (other.imageSmall != null)
				return false;
		} else if (!imageSmall.equals(other.imageSmall))
			return false;
		if (texts == null) {
			if (other.texts != null)
				return false;
		} else if (!texts.equals(other.texts))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
