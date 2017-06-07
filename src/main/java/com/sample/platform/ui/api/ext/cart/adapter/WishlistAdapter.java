package com.sample.platform.ui.api.ext.cart.adapter;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import com.sample.platform.ui.api.ext.cart.model.CartWishlist;
import com.sample.platform.ui.api.ext.cart.model.CartWishlistItem;
import com.sample.platform.ui.api.model.Wishlist;
import com.sample.platform.ui.api.model.WishlistItem;

public class WishlistAdapter extends ListenableFutureAdapter<Wishlist, ResponseEntity<CartWishlist>> {
	public WishlistAdapter(ListenableFuture<ResponseEntity<CartWishlist>> wishlist) {
		super(wishlist);
	}
	
	@Override
	protected Wishlist adapt(ResponseEntity<CartWishlist> responseEntity) throws ExecutionException {
		CartWishlist wishlist = responseEntity.getBody();
		
		List<CartWishlistItem> cartWishlistItems = wishlist.getItems();
		List<WishlistItem> wishlistItems = cartWishlistItems.stream().map(toWishlistItem).collect(Collectors.toList());
		
		return new Wishlist(wishlistItems);
	}
	
	private static Function<CartWishlistItem, WishlistItem> toWishlistItem = wishlistItem -> {
		return new WishlistItem(wishlistItem.getTitle(),
				wishlistItem.getSubtitle(),
				wishlistItem.getPrice(),
				wishlistItem.getOfferId(),
				wishlistItem.getImage(),
				wishlistItem.getId());
	};
}
