package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PreviewBody {
	@JsonProperty("formData")
	private List<PreviewBodyFormData> formData;

	public List<PreviewBodyFormData> getFormData() {
		return formData;
	}

	public void setFormData(List<PreviewBodyFormData> formData) {
		this.formData = formData;
	}

	public PreviewBody(List<PreviewBodyFormData> formData) {
		this.formData = formData;
	}

	public PreviewBody() {
		
	}
}