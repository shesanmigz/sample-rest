package com.sample.platform.ui.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Joiner;
import com.sample.platform.ui.api.AppProperties;
import com.sample.platform.ui.api.ShopApiConstants;

@RequestMapping(ShopApiConstants.BASE_PATH)
@Component
public class BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private AppProperties properties;
	
	public String getLocalizationUrl() {
		return properties.getLocalizationUrl();
	}
	
	public String getOrderUrl() {
		return properties.getOrderUrl();
	}
	
	public String getInventoryUrl() {
		return properties.getInventoryUrl();
	}
	
	public String getContentUrl() {
		return properties.getContentUrl();
	}
	
	public String getCartUrl() {
		return properties.getCartUrl();
	}
	
	public String getUserUrl() {
		return properties.getUserUrl();
	}
	
	public String getSearchUrl() {
		return properties.getSearchUrl();
	}
	
	public String getCheckoutUrl() {
		return properties.getCheckoutUrl();
	}
	
	public String getAdvertisementUrl() {
		return properties.getAdvertisementUrl();
	}
	
	public String getRecommendationUrl() {
		return properties.getRecommendationUrl();
	}
	
	public String getReviewUrl() {
		return properties.getReviewUrl();
	}
	
	public String getMessagingUrl() {
		return properties.getMessagingUrl();
	}
	
	public <I, T> DeferredResult<ResponseEntity<T>> handle(final HttpHeaders requestHeaders, final ControllerStrategy<I, T> strategy) throws Exception {
		DeferredResult<ResponseEntity<T>> deferredResult = new DeferredResult<>();

		if (strategy.isValid()) {
			String moduleURL = strategy.getBaseURL();
			if (moduleURL.isEmpty()) {
				deferredResult.setResult(new ResponseEntity<>(strategy.getStaticInfo(), HttpStatus.OK));
			} else {
				UriComponents uri = strategy.getPath(UriComponentsBuilder.fromUriString(moduleURL)).build();
				ListenableFuture<I> info = strategy.getInfo(uri.toUriString());
				info.addCallback(new ListenableFutureCallback<I>() {
						@Override
						public void onSuccess(final I result) {
							deferredResult.setResult(new ResponseEntity<>(strategy.convert(result), HttpStatus.OK));
						}
						
						@Override
						public void onFailure(final Throwable t) {
							if (t instanceof HttpStatusCodeException) {
								HttpStatusCodeException httpException = (HttpStatusCodeException) t;
								deferredResult.setResult(new ResponseEntity<>(httpException.getStatusCode()));
							} else {
								deferredResult.setResult(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));
							}
						}
				});
			}
		} else {
			String headers = " <no headers>";
			if (!requestHeaders.isEmpty()) {
				headers = "\n\t" + Joiner.on("\n\t").join(requestHeaders.entrySet().stream().map(e -> {
					String value = e.getKey();
					if (e.getValue() != null && !e.getValue().isEmpty()) {
						value+= ": ";
						if (e.getValue().size() > 1) {
							value+= Joiner.on(",").join(e.getValue().stream().map(v -> "{" + v + "}").iterator());
						} else {
							value+= e.getValue().iterator().next();
						}
					}
					return value;
				}).iterator());
			}
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			LOGGER.debug("Validation failed for {} with headers:{}", request.getRequestURI(), headers);

			deferredResult.setResult(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
		}
		
		return deferredResult;
	}
}