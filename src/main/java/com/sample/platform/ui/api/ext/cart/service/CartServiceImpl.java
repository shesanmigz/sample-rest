package com.sample.platform.ui.api.ext.cart.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.platform.ui.api.AppProperties;
import com.sample.platform.ui.api.adapter.ShopApiListenableFutureStringAdapter;
import com.sample.platform.ui.api.ext.cart.adapter.CartAdapter;
import com.sample.platform.ui.api.ext.cart.adapter.CartCountAdapter;
import com.sample.platform.ui.api.ext.cart.adapter.CartDeleteQuantityAdapter;
import com.sample.platform.ui.api.ext.cart.adapter.WishlistAdapter;
import com.sample.platform.ui.api.ext.cart.adapter.WishlistCountAdapter;
import com.sample.platform.ui.api.ext.cart.model.CartCart;
import com.sample.platform.ui.api.ext.cart.model.CartCartDeleteQuantity;
import com.sample.platform.ui.api.ext.cart.model.CartWishlist;
import com.sample.platform.ui.api.model.Cart;
import com.sample.platform.ui.api.model.CartCount;
import com.sample.platform.ui.api.model.CartDeleteQuantity;
import com.sample.platform.ui.api.model.CartItemBody;
import com.sample.platform.ui.api.model.CartItemQuantityBody;
import com.sample.platform.ui.api.model.Wishlist;
import com.sample.platform.ui.api.model.WishlistCount;
import com.sample.platform.ui.api.model.WishlistItemBody;
import com.sample.platform.ui.api.service.AsyncRestTemplateFactory;
import com.sample.platform.ui.api.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private AppProperties properties;
	
	@Autowired
	private AsyncRestTemplateFactory asyncRestTemplateFactory;
	
	@Override
	public ListenableFuture<Cart> getSessionCart(String url, HttpHeaders headers) {
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory
				.createShopAPIAsyncRestTemplate(
				Integer.parseInt(properties.getCartTimeout()));
		
		ListenableFuture<ResponseEntity<CartCart>> cart = asyncRestTemplate.exchange(url,
				HttpMethod.GET, new HttpEntity<String>(headers),
				new ParameterizedTypeReference<CartCart>() {
				});
		
		return new CartAdapter(cart);
	}
	
	@Override
	public Cart getStaticCart() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		InputStream inJson = Cart.class.getResourceAsStream("/static/cart/cart.json");
		
		Cart cart = objectMapper.readValue(inJson, Cart.class);
		
		return cart;
	}

	@Override
	public ListenableFuture<ResponseEntity<String>> postCartItem(String url, CartItemBody cartItemBody, HttpHeaders headers) {
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory
				.createShopAPIAsyncRestTemplate(
				Integer.parseInt(properties.getCartTimeout()));
		
		HttpEntity<CartItemBody> entity = new HttpEntity<CartItemBody>(cartItemBody, headers);
		
		ListenableFuture<ResponseEntity<String>> cart = asyncRestTemplate.exchange(url,
				HttpMethod.POST, entity,
				new ParameterizedTypeReference<String>() {
				});
		
		return cart;
	}

	@Override
	public ListenableFuture<CartDeleteQuantity> deleteSessionCartItem(String url, HttpHeaders headers) {
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory
				.createShopAPIAsyncRestTemplate(
				Integer.parseInt(properties.getCartTimeout()));
		
		ListenableFuture<ResponseEntity<CartCartDeleteQuantity>> cartCartDeleteQuantity = asyncRestTemplate.exchange(url,
				HttpMethod.DELETE, new HttpEntity<String>(headers),
				new ParameterizedTypeReference<CartCartDeleteQuantity>() {
				});
		
		return new CartDeleteQuantityAdapter(cartCartDeleteQuantity);
	}

	@Override
	public CartDeleteQuantity getStaticCartDeleteQuantity()
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		InputStream inJson = CartDeleteQuantity.class.getResourceAsStream("/static/cart/cartdeletequantity.json");
		
		CartDeleteQuantity cartDeleteQuantity = objectMapper.readValue(inJson, CartDeleteQuantity.class);
		
		return cartDeleteQuantity;
	}

	@Override
	public ListenableFuture<String> putSessionCartItem(String url, CartItemQuantityBody cartItemQuantityBody, HttpHeaders headers) {
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory
				.createShopAPIAsyncRestTemplate(
				Integer.parseInt(properties.getCartTimeout()));
		
		ListenableFuture<ResponseEntity<String>> cartCartItemDelete = asyncRestTemplate.exchange(url,
				HttpMethod.PUT, new HttpEntity<CartItemQuantityBody>(cartItemQuantityBody, headers),
				new ParameterizedTypeReference<String>() {
				});
		
		return new ShopApiListenableFutureStringAdapter(cartCartItemDelete);
	}
	
	@Override
	public ListenableFuture<CartCount> getSessionCartCount(String url, HttpHeaders headers) {
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory
				.createShopAPIAsyncRestTemplate(
				Integer.parseInt(properties.getCartTimeout()));
		
		ListenableFuture<ResponseEntity<CartCount>> cartCount = asyncRestTemplate.exchange(url,
				HttpMethod.GET, new HttpEntity<String>(headers),
				new ParameterizedTypeReference<CartCount>() {
				});
		
		return new CartCountAdapter(cartCount);
	}
	
	@Override
	public CartCount getStaticCartCount() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		InputStream inJson = CartCount.class.getResourceAsStream("/static/cart/cartcount.json");
		
		CartCount cartCount = objectMapper.readValue(inJson, CartCount.class);
		
		return cartCount;
	}
	
	@Override
	public ListenableFuture<WishlistCount> getSessionWishlistCount(String url, HttpHeaders headers) {
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory
				.createShopAPIAsyncRestTemplate(
				Integer.parseInt(properties.getCartTimeout()));
		
		ListenableFuture<ResponseEntity<WishlistCount>> cartCount = asyncRestTemplate.exchange(url,
				HttpMethod.GET, new HttpEntity<String>(headers),
				new ParameterizedTypeReference<WishlistCount>() {
				});
		
		return new WishlistCountAdapter(cartCount);
	}
	
	@Override
	public WishlistCount getStaticWishlistCount() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		InputStream inJson = WishlistCount.class.getResourceAsStream("/static/cart/wishlistcount.json");
		
		WishlistCount wishlistCount = objectMapper.readValue(inJson, WishlistCount.class);
		
		return wishlistCount;
	}
	
	@Override
	public ListenableFuture<Wishlist> getSessionWishlist(String url, HttpHeaders headers) {
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory
				.createShopAPIAsyncRestTemplate(
				Integer.parseInt(properties.getCartTimeout()));
		
		ListenableFuture<ResponseEntity<CartWishlist>> wishlist = asyncRestTemplate.exchange(url,
				HttpMethod.GET, new HttpEntity<String>(headers),
				new ParameterizedTypeReference<CartWishlist>() {
				});
		
		return new WishlistAdapter(wishlist);
	}
	
	@Override
	public Wishlist getStaticWishlist() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		InputStream inJson = Wishlist.class.getResourceAsStream("/static/cart/wishlist.json");
		
		Wishlist wishlist = objectMapper.readValue(inJson, Wishlist.class);
		
		return wishlist;
	}
	
	@Override
	public ListenableFuture<ResponseEntity<String>> postWishlistItem(String url, WishlistItemBody wishlistItemBody, HttpHeaders headers) {
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory
				.createShopAPIAsyncRestTemplate(
				Integer.parseInt(properties.getCartTimeout()));
		
		HttpEntity<WishlistItemBody> entity = new HttpEntity<WishlistItemBody>(wishlistItemBody, headers);
		
		ListenableFuture<ResponseEntity<String>> wishlist = asyncRestTemplate.exchange(url,
				HttpMethod.POST, entity,
				new ParameterizedTypeReference<String>() {
				});
		
		return wishlist;
	}
	
	@Override
	public ListenableFuture<String> deleteWishlistItem(String url, HttpHeaders headers) throws JsonParseException, JsonMappingException, IOException {
		AsyncRestTemplate asyncRestTemplate = this.asyncRestTemplateFactory
				.createShopAPIAsyncRestTemplate(
				Integer.parseInt(properties.getCartTimeout()));
		
		ListenableFuture<ResponseEntity<String>> cartWishlistItemDelete = asyncRestTemplate.exchange(url,
				HttpMethod.DELETE, new HttpEntity<String>(headers),
				new ParameterizedTypeReference<String>() {
				});
		
		return new ShopApiListenableFutureStringAdapter(cartWishlistItemDelete);
	}
}
