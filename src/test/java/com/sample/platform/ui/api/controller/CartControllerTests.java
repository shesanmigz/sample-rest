package com.sample.platform.ui.api.controller;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;
import com.sample.platform.ui.api.Application;
import com.sample.platform.ui.api.ShopApiConstants;
import com.sample.platform.ui.api.model.BillItem;
import com.sample.platform.ui.api.model.Cart;
import com.sample.platform.ui.api.model.CartCount;
import com.sample.platform.ui.api.model.CartDeleteQuantity;
import com.sample.platform.ui.api.model.CartItem;
import com.sample.platform.ui.api.model.CartItemBody;
import com.sample.platform.ui.api.model.CartItemQuantityBody;
import com.sample.platform.ui.api.model.Wishlist;
import com.sample.platform.ui.api.model.WishlistCount;
import com.sample.platform.ui.api.model.WishlistItem;
import com.sample.platform.ui.api.model.WishlistItemBody;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@ContextConfiguration
public abstract class CartControllerTests extends ShopApiControllerTests {
	private static final String MODULE_PATH = ShopApiConstants.BASE_PATH + "/cart";
	private static final String CART_PATH = "/cart";
	private static final String COUNT_PATH = "/count";
	private static final String WISHLIST_PATH = "/wishlist";
	
	private static final Cart STUB_CART = new Cart(
			ImmutableList.of(
					new CartItem("Bear Song",
							"Cool Bear",
							2,
							BigDecimal.valueOf(44.55),
							"12345",
							"http://img.memecdn.com/a-little-song_o_647990.jpg",
							"12"),
					new CartItem(
							"Development",
							"Production",
							1,
							BigDecimal.valueOf(11.55),
							"44345",
							"https://i.memecaptain.com/gend_images/fwio-w.jpg",
							"11")),
			new BillItem("Overview", BigDecimal.valueOf(70), BigDecimal.valueOf(5), BigDecimal.valueOf(75), BigDecimal.valueOf(22), BigDecimal.valueOf(97)),
			ImmutableList.of(
					new BillItem("Sample CH Warehouse",
							BigDecimal.valueOf(20),
							BigDecimal.valueOf(5),
							BigDecimal.valueOf(25),
							BigDecimal.valueOf(7),
							BigDecimal.valueOf(32)),
					new BillItem("Sample DE Warehouse",
							BigDecimal.valueOf(10),
							BigDecimal.valueOf(0),
							BigDecimal.valueOf(10),
							BigDecimal.valueOf(5),
							BigDecimal.valueOf(15)),
					new BillItem("Sample Marketplace",
							BigDecimal.valueOf(40),
							BigDecimal.valueOf(0),
							BigDecimal.valueOf(40),
							BigDecimal.valueOf(10),
							BigDecimal.valueOf(50))));

	private static final CartCount STUB_CART_COUNT = new CartCount(0);

	private static final CartDeleteQuantity STUB_CART_DELETE_QUANTITY = new CartDeleteQuantity(3);

	private static final Wishlist STUB_WISHLIST = new Wishlist(ImmutableList.of(
			new WishlistItem("Bear Song",
					"Cool Bear",
					BigDecimal.valueOf(44.55),
					"12345",
					"http://img.memecdn.com/a-little-song_o_647990.jpg",
					"12"),
			new WishlistItem("Development",
					"Production",
					BigDecimal.valueOf(11.55),
					"44345",
					"https://i.memecaptain.com/gend_images/fwio-w.jpg",
					"11")));

	private static final WishlistCount STUB_WISHLIST_COUNT = new WishlistCount(0);
	
	private static final Cart RES_CART = new Cart(
			ImmutableList.of(
					new CartItem("Bear Song",
							"Cool Bear",
							2,
							BigDecimal.valueOf(44.55),
							"12345",
							"http://img.memecdn.com/a-little-song_o_647990.jpg",
							"12"),
					new CartItem(
							"Development",
							"Production",
							1,
							BigDecimal.valueOf(11.55),
							"44345",
							"https://i.memecaptain.com/gend_images/fwio-w.jpg",
							"11")),
			new BillItem("Overview", BigDecimal.valueOf(70), BigDecimal.valueOf(5), BigDecimal.valueOf(75), BigDecimal.valueOf(22), BigDecimal.valueOf(97)),
			ImmutableList.of(
					new BillItem("Sample CH Warehouse",
							BigDecimal.valueOf(20),
							BigDecimal.valueOf(5),
							BigDecimal.valueOf(25),
							BigDecimal.valueOf(7),
							BigDecimal.valueOf(32)),
					new BillItem("Sample DE Warehouse",
							BigDecimal.valueOf(10),
							BigDecimal.valueOf(0),
							BigDecimal.valueOf(10),
							BigDecimal.valueOf(5),
							BigDecimal.valueOf(15)),
					new BillItem("Sample Marketplace",
							BigDecimal.valueOf(40),
							BigDecimal.valueOf(0),
							BigDecimal.valueOf(40),
							BigDecimal.valueOf(10),
							BigDecimal.valueOf(50))));

	private static final CartCount RES_CART_COUNT = new CartCount(0);

	private static final CartDeleteQuantity RES_CART_DELETE_QUANTITY = new CartDeleteQuantity(3);

	private static final Wishlist RES_WISHLIST = new Wishlist(ImmutableList.of(
			new WishlistItem("Bear Song",
					"Cool Bear",
					BigDecimal.valueOf(44.55),
					"12345",
					"http://img.memecdn.com/a-little-song_o_647990.jpg",
					"12"),
			new WishlistItem("Development",
					"Production",
					BigDecimal.valueOf(11.55),
					"44345",
					"https://i.memecaptain.com/gend_images/fwio-w.jpg",
					"11")));
	
	private static final WishlistCount RES_WISHLIST_COUNT = new WishlistCount(0);
	
	private static final CartItemBody CART_ITEM_BODY = new CartItemBody("offerId", 0);
	
	private static final CartItemQuantityBody CART_ITEM_QUANTITY_BODY = new CartItemQuantityBody(0);
	
	private static final WishlistItemBody WISH_LIST_ITEM_BODY = new WishlistItemBody("offerId");
	
	@Test
	public void testSessionCart() throws Exception {
		this.stub()
				.withGET("/v1/cart")
				.withPOJO(STUB_CART)
				.build();

		this.test()
				.withGET(MODULE_PATH + CART_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.expectPOJO(RES_CART)
				.run();
	}
	
	@Test
	public void testSessionCartWithoutRequiredHeaders() throws Exception {
		this.test()
				.withGET(MODULE_PATH + CART_PATH)
				.expectStatus(HttpStatus.BAD_REQUEST)
				.runWithServer();
	}
	
	@Test
	public void testSessionCartTraceHeader() throws Exception {
		ServerCall sc = this.stub()
				.withGET("/v1/cart")
				.withPOJO(STUB_CART)
				.build();
		
		this.test()
				.withGET(MODULE_PATH + CART_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.expectServerHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.notExpectHeader(ShopApiConstants.TRACE_HEADER)
				.expectPOJO(RES_CART)
				.runWithServer();
		
		this.test()
				.withGET(MODULE_PATH + CART_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.expectServerHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.expectPOJO(RES_CART)
				.runWithServer();
	}
	
	@Test
	public void testPostCartItem() throws Exception {
		this.stub()
				.withPOST("/v1/cart")
				.build();
		
		this.test()
				.withPOST(MODULE_PATH + CART_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withPOJO(CART_ITEM_BODY)
				.expectEmptyBody()
				.run();
	}
	
	@Test
	public void testPostCartItemWithoutSessionHeader() throws Exception {
		this.test()
				.withPOST(MODULE_PATH + CART_PATH)
				.withPOJO(CART_ITEM_BODY)
				.expectStatus(HttpStatus.BAD_REQUEST)
				.runWithServer();
	}
	
	@Test
	public void testPostCartItemTraceHeader() throws Exception {
		ServerCall sc = this.stub()
				.withPOST("/v1/cart")
				.build();
		
		this.test()
				.withPOST(MODULE_PATH + CART_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.withPOJO(CART_ITEM_BODY)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.notExpectHeader(ShopApiConstants.TRACE_HEADER)
				.expectEmptyBody()
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.runWithServer();
			
		this.test()
				.withPOST(MODULE_PATH + CART_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withPOJO(CART_ITEM_BODY)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.expectEmptyBody()
				.expectServerCall(sc)
				.runWithServer();
	}
	
	@Test
	public void testSessionCartCount() throws Exception {
		this.stub()
				.withGET("/v1/cart/count")
				.withPOJO(STUB_CART_COUNT)
				.build();
		
		this.test()
				.withGET(MODULE_PATH + CART_PATH + COUNT_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.expectPOJO(RES_CART_COUNT)
				.run();
	}
	
	@Test
	public void testSessionCartCountWithoutSessionHeader() throws Exception {
		this.test()
				.withGET(MODULE_PATH + CART_PATH + COUNT_PATH)
				.expectStatus(HttpStatus.BAD_REQUEST)
				.runWithServer();
	}
	
	@Test
	public void testSessionCartCountTraceHeader() throws Exception {
		ServerCall sc = this.stub().withGET("/v1/cart/count")
				.withPOJO(STUB_CART_COUNT)
				.build();
		
		this.test()
				.withGET(MODULE_PATH + CART_PATH + COUNT_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.notExpectHeader(ShopApiConstants.TRACE_HEADER)
				.expectPOJO(RES_CART_COUNT)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.runWithServer();
		
		this.test()
				.withGET(MODULE_PATH + CART_PATH + COUNT_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.expectPOJO(RES_CART_COUNT)
				.expectServerCall(sc)
				.runWithServer();
	}
	
	@Test
	public void testPutSessionCartItem() throws Exception {
		this.stub()
				.withPUT("/v1/cart/1")
				.build();
		
		this.test()
				.withPUT(MODULE_PATH + CART_PATH + "/1")
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withPOJO(CART_ITEM_QUANTITY_BODY)
				.expectEmptyBody()
				.run();
	}
	
	@Test
	public void testPutSessionCartItemWithoutSessionHeader() throws Exception {
		this.test()
				.withPUT(MODULE_PATH + CART_PATH + "/1")
				.withPOJO(CART_ITEM_QUANTITY_BODY)
				.expectStatus(HttpStatus.BAD_REQUEST)
				.runWithServer();
	}
	
	@Test
	public void testPutSessionCartItemTraceHeader() throws Exception {
		ServerCall sc = this.stub()
				.withPUT("/v1/cart/1")
				.build();
		
		this.test()
				.withPUT(MODULE_PATH + CART_PATH + "/1")
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.withPOJO(CART_ITEM_QUANTITY_BODY)
				.expectEmptyBody()
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.notExpectHeader(ShopApiConstants.TRACE_HEADER)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.runWithServer();
		
		this.test()
				.withPUT(MODULE_PATH + CART_PATH + "/1")
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withPOJO(CART_ITEM_QUANTITY_BODY)
				.expectEmptyBody()
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.expectServerCall(sc)
				.runWithServer();
	}
	
	@Test
	public void testDeleteSessionCartItem() throws Exception {
		this.stub()
				.withDELETE("/v1/cart/1")
				.withPOJO(STUB_CART_DELETE_QUANTITY)
				.build();
		
		this.test()
				.withDELETE(MODULE_PATH + CART_PATH + "/1")
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.expectPOJO(RES_CART_DELETE_QUANTITY)
				.run();
	}
	
	@Test
	public void testDeleteCartItemWithoutSessionHeader() throws Exception {
		this.test()
				.withDELETE(MODULE_PATH + CART_PATH + "/1")
				.expectStatus(HttpStatus.BAD_REQUEST)
				.runWithServer();
	}
	
	@Test
	public void testDeleteSessionCartItemTraceHeader() throws Exception {
		ServerCall sc = this.stub()
				.withDELETE("/v1/cart/1")
				.withPOJO(STUB_CART_DELETE_QUANTITY)
				.build();
		
		this.test()
				.withDELETE(MODULE_PATH + CART_PATH + "/1")
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.notExpectHeader(ShopApiConstants.TRACE_HEADER)
				.expectPOJO(RES_CART_DELETE_QUANTITY)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.runWithServer();
		
		this.test()
				.withDELETE(MODULE_PATH + CART_PATH + "/1")
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.expectPOJO(RES_CART_DELETE_QUANTITY)
				.expectServerCall(sc)
				.runWithServer();
	}
	
	@Test
	public void testSessionWishlist() throws Exception {
		this.stub()
				.withGET("/v1/wishlist")
				.withPOJO(STUB_WISHLIST)
				.build();
		
		this.test()
				.withGET(MODULE_PATH + WISHLIST_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.run();
	}
	
	@Test
	public void testSessionWishlistWithoutSessionHeader() throws Exception {
		this.test()
				.withGET(MODULE_PATH + WISHLIST_PATH)
				.expectStatus(HttpStatus.BAD_REQUEST)
				.runWithServer();
	}
	
	@Test
	public void testSessionWishlistTraceHeader() throws Exception {
		ServerCall sc = this.stub()
				.withGET("/v1/wishlist")
				.withPOJO(STUB_WISHLIST)
				.build();
		
		this.test().withGET(MODULE_PATH + WISHLIST_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.notExpectHeader(ShopApiConstants.TRACE_HEADER)
				.expectPOJO(RES_WISHLIST)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.runWithServer();
		
		this.test().withGET(MODULE_PATH + WISHLIST_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.expectPOJO(RES_WISHLIST)
				.expectServerCall(sc)
				.runWithServer();
	}
	
	@Test
	public void testPostWishlistItem() throws Exception {
		this.stub()
				.withPOST("/v1/wishlist")
				.build();
		
		this.test().withPOST(MODULE_PATH + WISHLIST_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withPOJO(WISH_LIST_ITEM_BODY)
				.expectEmptyBody()
				.run();
	}
	
	@Test
	public void testPostWishlistItemWithoutSessionHeader() throws Exception {
		this.test()
				.withPOST(MODULE_PATH + WISHLIST_PATH)
				.withPOJO(new WishlistItemBody("offerId"))
				.expectStatus(HttpStatus.BAD_REQUEST)
				.runWithServer();
	}
	
	@Test
	public void testPostWishlistItemTraceHeader() throws Exception {
		ServerCall sc = this.stub()
				.withPOST("/v1/wishlist")
				.build();
		
		this.test()
				.withPOST(MODULE_PATH + WISHLIST_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.withPOJO(WISH_LIST_ITEM_BODY)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.notExpectHeader(ShopApiConstants.TRACE_HEADER)
				.expectEmptyBody()
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.runWithServer();
		
		this.test()
				.withPOST(MODULE_PATH + WISHLIST_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withPOJO(WISH_LIST_ITEM_BODY)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.expectEmptyBody()
				.expectServerCall(sc)
				.runWithServer();
	}
	
	@Test
	public void testSessionWishlistCount() throws Exception {
		this.stub()
				.withGET("/v1/wishlist/count")
				.withPOJO(STUB_WISHLIST_COUNT)
				.build();
		
		this.test()
				.withGET(MODULE_PATH + WISHLIST_PATH + COUNT_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.expectPOJO(RES_WISHLIST_COUNT)
				.run();
	}
	
	@Test
	public void testSessionWishlistCountWithoutSessionHeader() throws Exception {
		this.test()
				.withGET(MODULE_PATH + WISHLIST_PATH + COUNT_PATH)
				.expectStatus(HttpStatus.BAD_REQUEST)
				.runWithServer();
	}
	
	@Test
	public void testSessionWishlistCountTraceHeader() throws Exception {
		ServerCall sc = this.stub()
				.withGET("/v1/wishlist/count")
				.withPOJO(STUB_WISHLIST_COUNT)
				.build();
		
		this.test()
				.withGET(MODULE_PATH + WISHLIST_PATH + COUNT_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.notExpectHeader(ShopApiConstants.TRACE_HEADER)
				.expectPOJO(RES_WISHLIST_COUNT)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.runWithServer();
		
		this.test()
				.withGET(MODULE_PATH + WISHLIST_PATH + COUNT_PATH)
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.expectPOJO(RES_WISHLIST_COUNT)
				.expectServerCall(sc)
				.runWithServer();
	}
	
	@Test
	public void testDeleteSessionWishlistItem() throws Exception {
		this.stub()
				.withDELETE("/v1/wishlist/1")
				.build();
		
		this.test().withDELETE(MODULE_PATH + WISHLIST_PATH + "/1")
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.expectEmptyBody()
				.runWithServer();
	}
	
	@Test
	public void testDeleteSessionWishlistItemWithoutSessionHeader() throws Exception {
		this.test()
				.withDELETE(MODULE_PATH + WISHLIST_PATH + "/1")
				.expectStatus(HttpStatus.BAD_REQUEST)
				.runWithServer();
	}
	
	@Test
	public void testDeleteSessionWishlistItemTraceHeader() throws Exception {
		ServerCall sc = this.stub()
				.withDELETE("/v1/wishlist/1")
				.build();
		
		this.test()
				.withDELETE(MODULE_PATH + WISHLIST_PATH + "/1")
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.withHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.notExpectHeader(ShopApiConstants.TRACE_HEADER)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, TRACE_HEADER_VALUE)
				.runWithServer();
		
		this.test()
				.withDELETE(MODULE_PATH + WISHLIST_PATH + "/1")
				.withHeader(ShopApiConstants.SESSION_HEADER, SESSION_HEADER_VALUE)
				.withHeader(ShopApiConstants.DOMAIN_HEADER, DOMAIN_HEADER_VALUE)
				.notExpectHeader(ShopApiConstants.SESSION_HEADER)
				.notExpectHeader(ShopApiConstants.DOMAIN_HEADER)
				.expectServerCall(sc)
				.runWithServer();
	}
}