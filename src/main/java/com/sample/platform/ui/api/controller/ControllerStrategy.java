package com.sample.platform.ui.api.controller;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The common behaviour of controllers has been extracted to the
 * {@link BaseController#handle(org.springframework.http.HttpHeaders, ControllerStrategy)}
 * method, but controller specifics should be handled by implementations of this
 * strategy.
 */
public interface ControllerStrategy<I, T> {
	/**
	 * Base URL of all controller requests. Usually this is a module path.
	 */
	String getBaseURL();
	
	/**
	 * Returns a builder of the URI needed to send requests to an external service
	 * to complete our own request.
	 * 
	 * @param path
	 *          a URI builder with the base URL preset.
	 */
	UriComponentsBuilder getPath(UriComponentsBuilder path);
	
	/**
	 * Returns false if for some reason the request is cannot be completed due to
	 * base input data. In this case a HTTP 400 Bad Request will be sent back to
	 * the caller.
	 */
	boolean isValid() throws Exception;
	
	/**
	 * Returns data for the request when external services are disabled.
	 */
	T getStaticInfo() throws Exception;
	
	/**
	 * Returns data for the request when external services are enabled.
	 * 
	 * @param uri
	 *          URI of an external resource.
	 */
	ListenableFuture<I> getInfo(String uri) throws Exception;
	
	/**
	 * Converts data in the format as returned by an external service to the data
	 * expected by the caller.
	 * 
	 * @param info
	 *          data to convert
	 */
	T convert(I info);
}
