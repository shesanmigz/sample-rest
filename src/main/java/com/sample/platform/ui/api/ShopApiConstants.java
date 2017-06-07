package com.sample.platform.ui.api;

public final class ShopApiConstants {
	public static final String BASE_PATH = "/api/v1";
	
	public static final String DOMAIN_HEADER = "X-Sample-Domain";
	public static final String TRACE_HEADER = "X-Sample-Trace";
	public static final String LANGUAGE_HEADER = "X-Sample-Language";
	public static final String SESSION_HEADER = "X-Sample-Session";
	public static final String LOCATION_HEADER = "X-Sample-Location";
	public static final String COMMIT_HEADER = "X-Sample-Commit";
	public static final String CHECKOUT_HEADER = "X-Sample-Checkout";
	public static final String REMOTE_IP_HEADER = "X-Sample-RemoteIp";
	public static final String CONTENT_TYPE_HEADER = "Content-Type";
	
	public static final String DOMAIN_HEADER_DESCRIPTION = "Domain identifier";
	public static final String TRACE_HEADER_DESCRIPTION = "Trace identifier";
	public static final String LANGUAGE_HEADER_DESCRIPTION = "Language identifier";
	public static final String SESSION_HEADER_DESCRIPTION = "Session identifier";
	public static final String LOCATION_HEADER_DESCRIPTION = "Location of the user (latitude and langitude OR Address)";
	public static final String COMMIT_HEADER_DESCRIPTION = "The Git commit hash of the deployed version";
	public static final String CHECKOUT_HEADER_DESCRIPTION = "Checkout identifier";
	public static final String REMOTE_IP_HEADER_DESCRIPTION = "Remote ip of the browser";
	
	public ShopApiConstants() {
		
	}
}
