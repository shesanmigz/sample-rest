package com.sample.platform.ui.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponentsBuilder;

import com.sample.platform.ui.api.ShopApiConstants;
import com.sample.platform.ui.api.model.Cart;
import com.sample.platform.ui.api.model.CartCount;
import com.sample.platform.ui.api.model.CartDeleteQuantity;
import com.sample.platform.ui.api.model.CartItemBody;
import com.sample.platform.ui.api.model.CartItemQuantityBody;
import com.sample.platform.ui.api.model.Wishlist;
import com.sample.platform.ui.api.model.WishlistCount;
import com.sample.platform.ui.api.model.WishlistItemBody;
import com.sample.platform.ui.api.service.CartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "Cart Controller")
public class CartController extends BaseController {
	@Autowired
	private CartService cartService;
	
	private static final String MODULE_PATH = "/cart";
	private static final String CART_PATH = "/cart";
	private static final String COUNT_PATH = "/count";
	private static final String WISHLIST_PATH = "/wishlist";
	
	private abstract class CartStrategy<I, T> extends BaseControllerStrategy<I, T> {
		private final HttpHeaders requestHeaders;
		
		public CartStrategy(final HttpHeaders requestHeaders) {
			this.requestHeaders = requestHeaders;
		}
		
		@Override
		public String getBaseURL() {
			return CartController.this.getCartUrl();
		}
		
		@Override
		public boolean isValid() throws Exception {
			return this.requestHeaders.get(ShopApiConstants.SESSION_HEADER) != null
					&& !this.requestHeaders.get(ShopApiConstants.SESSION_HEADER).isEmpty();
		}
	}
	
	@RequestMapping(value = CartController.MODULE_PATH
			+ CartController.CART_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Get the Cart for the session", notes = "Get the Cart for the session", response = Cart.class, responseContainer = "Object")
	@ApiImplicitParams({
			@ApiImplicitParam(name = ShopApiConstants.SESSION_HEADER, value = ShopApiConstants.SESSION_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.DOMAIN_HEADER, value = ShopApiConstants.DOMAIN_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.LANGUAGE_HEADER, value = ShopApiConstants.LANGUAGE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.TRACE_HEADER, value = ShopApiConstants.TRACE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header") })
	public DeferredResult<ResponseEntity<Cart>> getSessionCart(
			@RequestHeader(required = true) final HttpHeaders requestHeaders) throws Exception {
		return this.handle(requestHeaders, this.new CartStrategy<Cart, Cart>(requestHeaders) {
			@Override
			public UriComponentsBuilder getPath(final UriComponentsBuilder path) {
				return path.path(CartController.CART_PATH);
			}
			
			@Override
			public Cart getStaticInfo() throws Exception {
				return CartController.this.cartService.getStaticCart();
			}
			
			@Override
			public ListenableFuture<Cart> getInfo(final String uri) {
				return CartController.this.cartService.getSessionCart(uri, requestHeaders);
			}
		});
	}
	
	@RequestMapping(value = CartController.MODULE_PATH
			+ CartController.CART_PATH, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Add new item to the Cart", notes = "Add new item to the Cart", response = String.class, responseContainer = "Object")
	@ApiImplicitParams({
			@ApiImplicitParam(name = ShopApiConstants.SESSION_HEADER, value = ShopApiConstants.SESSION_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.DOMAIN_HEADER, value = ShopApiConstants.DOMAIN_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.LANGUAGE_HEADER, value = ShopApiConstants.LANGUAGE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.TRACE_HEADER, value = ShopApiConstants.TRACE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header") })
	public DeferredResult<ResponseEntity<String>> postCartItem(
			@RequestHeader(required = true) final HttpHeaders requestHeaders,
			@ApiParam(name = "body", value = "body", required = true) @RequestBody(required = true) final CartItemBody body)
			throws Exception {
		
		return this.handle(requestHeaders, new CartStrategy<ResponseEntity<String>, String>(requestHeaders) {
			@Override
			public UriComponentsBuilder getPath(final UriComponentsBuilder path) {
				return path.path(CartController.CART_PATH);
			}
			
			@Override
			public String getStaticInfo() throws Exception {
				return StringUtils.EMPTY;
			}
			
			@Override
			public ListenableFuture<ResponseEntity<String>> getInfo(final String uri) throws Exception {
				return CartController.this.cartService.postCartItem(uri, body, requestHeaders);
			}
			
			@Override
			public String convert(final ResponseEntity<String> info) {
				return StringUtils.EMPTY;
			}
		});
	}
	
	@RequestMapping(value = CartController.MODULE_PATH + CartController.CART_PATH
			+ CartController.COUNT_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Get the number of items in the cart", notes = "Get the number of items in the cart", response = CartCount.class, responseContainer = "Object")
	@ApiImplicitParams({
			@ApiImplicitParam(name = ShopApiConstants.SESSION_HEADER, value = ShopApiConstants.SESSION_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.DOMAIN_HEADER, value = ShopApiConstants.DOMAIN_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.LANGUAGE_HEADER, value = ShopApiConstants.LANGUAGE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.TRACE_HEADER, value = ShopApiConstants.TRACE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header") })
	public DeferredResult<ResponseEntity<CartCount>> getSessionCartCount(
			@RequestHeader(required = true) final HttpHeaders requestHeaders) throws Exception {
		
		return this.handle(requestHeaders, this.new CartStrategy<CartCount, CartCount>(requestHeaders) {
			@Override
			public UriComponentsBuilder getPath(final UriComponentsBuilder path) {
				return path.path(CartController.CART_PATH).path(CartController.COUNT_PATH);
			}
			
			@Override
			public CartCount getStaticInfo() throws Exception {
				return CartController.this.cartService.getStaticCartCount();
			}
			
			@Override
			public ListenableFuture<CartCount> getInfo(final String uri) throws Exception {
				return CartController.this.cartService.getSessionCartCount(uri, requestHeaders);
			}
		});
	}
	
	@RequestMapping(value = CartController.MODULE_PATH + CartController.CART_PATH
			+ "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Add or remove quantity for id cart item", notes = "Add or remove quantity for id cart item", response = String.class, responseContainer = "Object")
	@ApiImplicitParams({
			@ApiImplicitParam(name = ShopApiConstants.SESSION_HEADER, value = ShopApiConstants.SESSION_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.DOMAIN_HEADER, value = ShopApiConstants.DOMAIN_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.LANGUAGE_HEADER, value = ShopApiConstants.LANGUAGE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.TRACE_HEADER, value = ShopApiConstants.TRACE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header") })
	public DeferredResult<ResponseEntity<String>> putSessionCartItem(
			@RequestHeader(required = true) final HttpHeaders requestHeaders,
			@PathVariable(required = true) final Integer id,
			@ApiParam(name = "body", value = "body", required = true) @RequestBody(required = true) final CartItemQuantityBody body) throws Exception {
		return this.handle(requestHeaders, this.new CartStrategy<String, String>(requestHeaders) {
			@Override
			public UriComponentsBuilder getPath(final UriComponentsBuilder path) {
				return path.path(CartController.CART_PATH).path("/" + id);
			}
			
			@Override
			public String getStaticInfo() throws Exception {
				return StringUtils.EMPTY;
			}
			
			@Override
			public ListenableFuture<String> getInfo(final String uri) throws Exception {
				return CartController.this.cartService.putSessionCartItem(uri, body, requestHeaders);
			}
		});
	}
	
	@RequestMapping(value = CartController.MODULE_PATH + CartController.CART_PATH
			+ "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Remove id cart item from the Cart", notes = "Remove id cart item from the Cart", response = CartDeleteQuantity.class, responseContainer = "Object")
	@ApiImplicitParams({
			@ApiImplicitParam(name = ShopApiConstants.SESSION_HEADER, value = ShopApiConstants.SESSION_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.DOMAIN_HEADER, value = ShopApiConstants.DOMAIN_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.LANGUAGE_HEADER, value = ShopApiConstants.LANGUAGE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.TRACE_HEADER, value = ShopApiConstants.TRACE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header") })
	public DeferredResult<ResponseEntity<CartDeleteQuantity>> deleteSessionCartItem(
			@RequestHeader(required = true) final HttpHeaders requestHeaders,
			@PathVariable(required = true) final Integer id) throws Exception {
		
		return this.handle(requestHeaders, this.new CartStrategy<CartDeleteQuantity, CartDeleteQuantity>(requestHeaders) {
			@Override
			public UriComponentsBuilder getPath(final UriComponentsBuilder path) {
				return path.path(CartController.CART_PATH).path("/" + id);
			}
			
			@Override
			public CartDeleteQuantity getStaticInfo() throws Exception {
				return CartController.this.cartService.getStaticCartDeleteQuantity();
			}
			
			@Override
			public ListenableFuture<CartDeleteQuantity> getInfo(final String uri) throws Exception {
				return CartController.this.cartService.deleteSessionCartItem(uri, requestHeaders);
			}
		});
	}
	
	@RequestMapping(value = CartController.MODULE_PATH
			+ CartController.WISHLIST_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Get the Wishlist for the session", notes = "Get the Wishlist for the session", response = Wishlist.class, responseContainer = "Object")
	@ApiImplicitParams({
			@ApiImplicitParam(name = ShopApiConstants.SESSION_HEADER, value = ShopApiConstants.SESSION_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.DOMAIN_HEADER, value = ShopApiConstants.DOMAIN_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.LANGUAGE_HEADER, value = ShopApiConstants.LANGUAGE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.TRACE_HEADER, value = ShopApiConstants.TRACE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header") })
	public DeferredResult<ResponseEntity<Wishlist>> getSessionWishlist(
			@RequestHeader(required = true) final HttpHeaders requestHeaders) throws Exception {
		return this.handle(requestHeaders, this.new CartStrategy<Wishlist, Wishlist>(requestHeaders) {
			@Override
			public UriComponentsBuilder getPath(final UriComponentsBuilder path) {
				return path.path(CartController.WISHLIST_PATH);
			}
			
			@Override
			public Wishlist getStaticInfo() throws Exception {
				return CartController.this.cartService.getStaticWishlist();
			}
			
			@Override
			public ListenableFuture<Wishlist> getInfo(final String uri) throws Exception {
				return CartController.this.cartService.getSessionWishlist(uri, requestHeaders);
			}
		});
	}
	
	@RequestMapping(value = CartController.MODULE_PATH
			+ CartController.WISHLIST_PATH, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Add new item to the Wishlist", notes = "Add new item to the Wishlist", response = String.class, responseContainer = "Object")
	@ApiImplicitParams({
			@ApiImplicitParam(name = ShopApiConstants.SESSION_HEADER, value = ShopApiConstants.SESSION_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.DOMAIN_HEADER, value = ShopApiConstants.DOMAIN_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.LANGUAGE_HEADER, value = ShopApiConstants.LANGUAGE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.TRACE_HEADER, value = ShopApiConstants.TRACE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header") })
	public DeferredResult<ResponseEntity<String>> postWishlistItem(
			@RequestHeader(required = true) final HttpHeaders requestHeaders,
			@ApiParam(name = "body", value = "body", required = true) @RequestBody(required = true) final WishlistItemBody body)
			throws Exception {
		
		return this.handle(requestHeaders, this.new CartStrategy<ResponseEntity<String>, String>(requestHeaders) {
			@Override
			public UriComponentsBuilder getPath(final UriComponentsBuilder path) {
				return path.path(CartController.WISHLIST_PATH);
			}
			
			@Override
			public String getStaticInfo() throws Exception {
				return StringUtils.EMPTY;
			}
			
			@Override
			public ListenableFuture<ResponseEntity<String>> getInfo(final String uri) throws Exception {
				return CartController.this.cartService.postWishlistItem(uri, body, requestHeaders);
			}
			
			@Override
			public String convert(final ResponseEntity<String> info) {
				return StringUtils.EMPTY;
			}
		});
	}
	
	@RequestMapping(value = CartController.MODULE_PATH + CartController.WISHLIST_PATH
			+ CartController.COUNT_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Get the number of items in the cart", notes = "Get the number of items in the cart", response = WishlistCount.class, responseContainer = "Object")
	@ApiImplicitParams({
			@ApiImplicitParam(name = ShopApiConstants.SESSION_HEADER, value = ShopApiConstants.SESSION_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.DOMAIN_HEADER, value = ShopApiConstants.DOMAIN_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.LANGUAGE_HEADER, value = ShopApiConstants.LANGUAGE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.TRACE_HEADER, value = ShopApiConstants.TRACE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header") })
	public DeferredResult<ResponseEntity<WishlistCount>> getSessionWishlistCount(
			@RequestHeader(required = true) final HttpHeaders requestHeaders) throws Exception {

		return this.handle(requestHeaders, this.new CartStrategy<WishlistCount, WishlistCount>(requestHeaders) {
			@Override
			public UriComponentsBuilder getPath(final UriComponentsBuilder path) {
				return path.path(CartController.WISHLIST_PATH).path(CartController.COUNT_PATH);
			}
			
			@Override
			public WishlistCount getStaticInfo() throws Exception {
				return CartController.this.cartService.getStaticWishlistCount();
			}
			
			@Override
			public ListenableFuture<WishlistCount> getInfo(final String uri) throws Exception {
				return CartController.this.cartService.getSessionWishlistCount(uri, requestHeaders);
			}
		});
	}
	
	@RequestMapping(value = CartController.MODULE_PATH + CartController.WISHLIST_PATH
			+ "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Remove id wishlist item from the Wishlist", notes = "Remove id wishlist item from the Wishlist", response = String.class, responseContainer = "Object")
	@ApiImplicitParams({
			@ApiImplicitParam(name = ShopApiConstants.SESSION_HEADER, value = ShopApiConstants.SESSION_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.DOMAIN_HEADER, value = ShopApiConstants.DOMAIN_HEADER_DESCRIPTION, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.LANGUAGE_HEADER, value = ShopApiConstants.LANGUAGE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = ShopApiConstants.TRACE_HEADER, value = ShopApiConstants.TRACE_HEADER_DESCRIPTION, required = false, dataType = "string", paramType = "header") })
	public DeferredResult<ResponseEntity<String>> deleteSessionWishlistItem(
			@RequestHeader(required = true) final HttpHeaders requestHeaders,
			@PathVariable(required = true) final Integer id) throws Exception {

		return this.handle(requestHeaders, this.new CartStrategy<String, String>(requestHeaders) {
			@Override
			public UriComponentsBuilder getPath(final UriComponentsBuilder path) {
				return path.path(CartController.WISHLIST_PATH).path("/" + id);
			}
			
			@Override
			public String getStaticInfo() throws Exception {
				return StringUtils.EMPTY;
			}
			
			@Override
			public ListenableFuture<String> getInfo(final String uri) throws Exception {
				return CartController.this.cartService.deleteWishlistItem(uri, requestHeaders);
			}
		});
	}
}