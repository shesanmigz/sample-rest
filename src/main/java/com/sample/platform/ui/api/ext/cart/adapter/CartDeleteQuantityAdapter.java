package com.sample.platform.ui.api.ext.cart.adapter;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import com.sample.platform.ui.api.ext.cart.model.CartCartDeleteQuantity;
import com.sample.platform.ui.api.model.CartDeleteQuantity;

public class CartDeleteQuantityAdapter extends ListenableFutureAdapter<CartDeleteQuantity, ResponseEntity<CartCartDeleteQuantity>> {
	public CartDeleteQuantityAdapter(ListenableFuture<ResponseEntity<CartCartDeleteQuantity>> cartCount) {
		super(cartCount);
	}
	
	@Override
	protected CartDeleteQuantity adapt(ResponseEntity<CartCartDeleteQuantity> responseEntity) throws ExecutionException {
		CartCartDeleteQuantity cartCartCount = responseEntity.getBody();
		
		return new CartDeleteQuantity(cartCartCount.getQuantity());
	}
}
