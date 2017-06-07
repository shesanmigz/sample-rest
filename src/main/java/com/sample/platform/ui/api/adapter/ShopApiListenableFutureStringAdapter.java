package com.sample.platform.ui.api.adapter;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

public class ShopApiListenableFutureStringAdapter extends ListenableFutureAdapter<String, ResponseEntity<String>> {
	public ShopApiListenableFutureStringAdapter(ListenableFuture<ResponseEntity<String>> future) {
		super(future);
	}
	
	@Override
	protected String adapt(ResponseEntity<String> responseEntity) throws ExecutionException {
		return responseEntity.getBody();
	}
}