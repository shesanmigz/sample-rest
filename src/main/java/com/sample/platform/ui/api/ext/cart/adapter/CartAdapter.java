package com.sample.platform.ui.api.ext.cart.adapter;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import com.sample.platform.ui.api.ext.cart.model.CartBillItem;
import com.sample.platform.ui.api.ext.cart.model.CartCart;
import com.sample.platform.ui.api.ext.cart.model.CartCartItem;
import com.sample.platform.ui.api.model.BillItem;
import com.sample.platform.ui.api.model.Cart;
import com.sample.platform.ui.api.model.CartItem;

public class CartAdapter extends ListenableFutureAdapter<Cart, ResponseEntity<CartCart>> {
	public CartAdapter(ListenableFuture<ResponseEntity<CartCart>> cart) {
		super(cart);
	}
	
	@Override
	protected Cart adapt(ResponseEntity<CartCart> responseEntity) throws ExecutionException {
		CartCart cart = responseEntity.getBody();
		
		List<CartItem> cartItems = null;
		List<CartCartItem> cartCartItems = cart.getItems();
		if (cartCartItems != null) {
			cartItems = cartCartItems.stream().map(toCartItem).collect(Collectors.toList());
		}
		
		BillItem billSummary = null;
		CartBillItem cartBillSummary = cart.getBillSummary();
		if (cartBillSummary != null) {
			billSummary = new BillItem(cartBillSummary.getTitle(),
					cartBillSummary.getSubtotal(),
					cartBillSummary.getShipping(),
					cartBillSummary.getNet(),
					cartBillSummary.getVat(),
					cartBillSummary.getTotal());
		}
		
		List<BillItem> bills = null;
		List<CartBillItem> cartBills = cart.getBills();
		if (cartBills != null) {
			bills = cartBills.stream().map(toBillItem).collect(Collectors.toList());
		}
		
		return new Cart(cartItems, billSummary, bills);
	}
	
	private static Function<CartBillItem, BillItem> toBillItem = billItem -> {
		return new BillItem(billItem.getTitle(),
				billItem.getSubtotal(),
				billItem.getShipping(),
				billItem.getNet(),
				billItem.getVat(),
				billItem.getTotal());
	};
	
	private static Function<CartCartItem, CartItem> toCartItem = cartItem -> {
		return new CartItem(cartItem.getTitle(),
				cartItem.getSubtitle(),
				cartItem.getQuantity(),
				cartItem.getPrice(),
				cartItem.getOfferId(),
				cartItem.getImage(),
				cartItem.getId());
	};
}
