package com.sample.platform.ui.api.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import com.sample.platform.ui.api.service.AsyncRestTemplateFactory;

@RestController
public class TraceIdController {
	@Autowired
	private AsyncRestTemplateFactory asyncRestTemplateFactory;
	
	@RequestMapping(value = "/trace-id-test", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public DeferredResult<String> traceIdTest(@RequestHeader(required = true) final HttpHeaders requestHeaders)
			throws Exception {
		DeferredResult<String> deferredResult = new DeferredResult<>();
		
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory.createShopAPIAsyncRestTemplate(5000);
		
		ListenableFuture<ResponseEntity<String>> result = asyncRestTemplate.exchange("http://localhost:8090/trace-id-test-callback",
				HttpMethod.GET, new HttpEntity<>(null), new ParameterizedTypeReference<String>() {
				});
		
		result.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
			@Override
			public void onSuccess(final ResponseEntity<String> result) {
				deferredResult.setResult(result.getBody());
			}
			
			@Override
			public void onFailure(final Throwable ex) {
				deferredResult.setResult("ERROR");
			}
		});
		
		return deferredResult;
	}

	@RequestMapping(value = "/trace-id-error-test", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public DeferredResult<String> traceIdErrorTest(@RequestHeader(required = true) final HttpHeaders requestHeaders)
			throws Exception {
		throw new Exception("FAILURE");
	}
}
