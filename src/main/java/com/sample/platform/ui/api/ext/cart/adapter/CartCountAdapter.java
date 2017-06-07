package com.sample.platform.ui.api.ext.cart.adapter;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import com.sample.platform.ui.api.model.CartCount;

public class CartCountAdapter extends ListenableFutureAdapter<CartCount, ResponseEntity<CartCount>> {
	public CartCountAdapter(ListenableFuture<ResponseEntity<CartCount>> cartCount) {
		super(cartCount);
	}
	
	@Override
	protected CartCount adapt(ResponseEntity<CartCount> responseEntity) throws ExecutionException {
		CartCount cartCartCount = responseEntity.getBody();
		
		return new CartCount(cartCartCount.getCount());
	}
}
