package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {
	@JsonProperty("releaseDate")
	private String releaseDate;

	@JsonProperty("catalogAddDate")
	private String catalogAddDate;

	@JsonProperty("manufacturerSRP")
	private BigDecimal manufacturerSRP;

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCatalogAddDate() {
		return catalogAddDate;
	}

	public void setCatalogAddDate(String catalogAddDate) {
		this.catalogAddDate = catalogAddDate;
	}

	public BigDecimal getManufacturerSRP() {
		return manufacturerSRP;
	}

	public void setManufacturerSRP(BigDecimal manufacturerSRP) {
		this.manufacturerSRP = manufacturerSRP;
	}

	public Article(String releaseDate, String catalogAddDate, BigDecimal manufacturerSRP) {
		this.releaseDate = releaseDate;
		this.catalogAddDate = catalogAddDate;
		this.manufacturerSRP = manufacturerSRP;
	}

	public Article() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catalogAddDate == null) ? 0 : catalogAddDate.hashCode());
		result = prime * result + ((manufacturerSRP == null) ? 0 : manufacturerSRP.hashCode());
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
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
		Article other = (Article) obj;
		if (catalogAddDate == null) {
			if (other.catalogAddDate != null)
				return false;
		} else if (!catalogAddDate.equals(other.catalogAddDate))
			return false;
		if (manufacturerSRP == null) {
			if (other.manufacturerSRP != null)
				return false;
		} else if (!manufacturerSRP.equals(other.manufacturerSRP))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		return true;
	}
}
