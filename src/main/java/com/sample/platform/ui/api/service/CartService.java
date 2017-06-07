package com.sample.platform.ui.api.service;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sample.platform.ui.api.model.Cart;
import com.sample.platform.ui.api.model.CartCount;
import com.sample.platform.ui.api.model.CartDeleteQuantity;
import com.sample.platform.ui.api.model.CartItemBody;
import com.sample.platform.ui.api.model.CartItemQuantityBody;
import com.sample.platform.ui.api.model.Wishlist;
import com.sample.platform.ui.api.model.WishlistCount;
import com.sample.platform.ui.api.model.WishlistItemBody;

public interface CartService {
	ListenableFuture<Cart> getSessionCart(String url, HttpHeaders headers);
	
	Cart getStaticCart() throws JsonParseException, JsonMappingException, IOException;
	
	ListenableFuture<ResponseEntity<String>> postCartItem(String url, CartItemBody cartItemBody, HttpHeaders headers);
	
	ListenableFuture<CartDeleteQuantity> deleteSessionCartItem(String url, HttpHeaders headers);
	
	CartDeleteQuantity getStaticCartDeleteQuantity() throws JsonParseException, JsonMappingException, IOException;
	
	ListenableFuture<String> putSessionCartItem(String url, CartItemQuantityBody body,HttpHeaders headers);
	
	ListenableFuture<CartCount> getSessionCartCount(String url, HttpHeaders headers);
	
	CartCount getStaticCartCount() throws JsonParseException, JsonMappingException, IOException;
	
	ListenableFuture<WishlistCount> getSessionWishlistCount(String url, HttpHeaders headers);
	
	WishlistCount getStaticWishlistCount() throws JsonParseException, JsonMappingException, IOException;
	
	ListenableFuture<Wishlist> getSessionWishlist(String url, HttpHeaders headers);
	
	Wishlist getStaticWishlist() throws JsonParseException, JsonMappingException, IOException;
	
	ListenableFuture<ResponseEntity<String>> postWishlistItem(String url, WishlistItemBody wishlistItemBody, HttpHeaders headers);
	
	ListenableFuture<String> deleteWishlistItem(String url, HttpHeaders headers) throws JsonParseException, JsonMappingException, IOException;
}
