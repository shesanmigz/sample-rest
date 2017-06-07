package com.sample.platform.ui.api.ext.cart.adapter;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import com.sample.platform.ui.api.model.WishlistCount;

public class WishlistCountAdapter extends ListenableFutureAdapter<WishlistCount, ResponseEntity<WishlistCount>> {
	public WishlistCountAdapter(ListenableFuture<ResponseEntity<WishlistCount>> wishlistCount) {
		super(wishlistCount);
	}
	
	@Override
	protected WishlistCount adapt(ResponseEntity<WishlistCount> responseEntity) throws ExecutionException {
		WishlistCount cartWishlistCount = responseEntity.getBody();
		
		return new WishlistCount(cartWishlistCount.getCount());
	}
}
