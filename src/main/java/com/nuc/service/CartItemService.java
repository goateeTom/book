package com.nuc.service;

import java.util.List;

import com.nuc.pojo.CartList;
import com.nuc.pojo.Cartitem;

public interface CartItemService {
	public List<CartList> myCart(String uid);

	public void add(Cartitem cartitem);

	public CartList updateQuantity(Cartitem cartitem);

	public void deleteByprimarykey(String string);

	public CartList selectByPrimarykey(String string);

}
