package com.nuc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuc.mapper.BookMapper;
import com.nuc.mapper.CartitemMapper;
import com.nuc.mapper.UserMapper;
import com.nuc.pojo.Book;
import com.nuc.pojo.CartList;
import com.nuc.pojo.Cartitem;
import com.nuc.pojo.CartitemExample;
import com.nuc.pojo.User;
import com.nuc.service.CartItemService;


@Service
public class CartItemServiceImpl implements CartItemService{
	@Autowired
	CartitemMapper cartitemMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired 
	BookMapper bookMapper;

	@Override
	public List<CartList> myCart(String uid) {
		
		CartitemExample example = new CartitemExample();
		CartitemExample.Criteria criteria= example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<CartList> cartLists =new ArrayList<CartList>();
		 List<Cartitem> cartitems= cartitemMapper.selectByExample(example);
		for (Cartitem cartitem : cartitems) {
			CartList cartList =new CartList();
			cartList.setCartItemId(cartitem.getCartItemId());
			cartList.setQuantity(cartitem.getQuantity());
			Book book = bookMapper.selectByPrimaryKey(cartitem.getBid());
			cartList.setBook(book);
			User user = userMapper.selectByPrimaryKey(cartitem.getUid());
			cartList.setUser(user);
			cartLists.add(cartList);
		}
		return cartLists;
	}
	@Override
	public void add(Cartitem cartitem) {
		CartitemExample cartitemExample = new CartitemExample();
		CartitemExample.Criteria cartCriteria = cartitemExample.createCriteria();
		cartCriteria.andBidEqualTo(cartitem.getBid());
		cartCriteria.andUidEqualTo(cartitem.getUid());
		List<Cartitem> cartitem2 = cartitemMapper.selectByExample(cartitemExample);
		if(cartitem2.isEmpty()){
			String str1=UUID.randomUUID().toString().replace("-", "");
			cartitem.setCartItemId(str1);
			cartitemMapper.insertSelective(cartitem);
		}else{
			int a =cartitem2.get(0).getQuantity();
			int b = cartitem.getQuantity();
			cartitem2.get(0).setQuantity(a+b);
			cartitemMapper.updateByPrimaryKeySelective(cartitem2.get(0));
		}
		
	}
	@Override
	public CartList updateQuantity(Cartitem cartitem) {
		cartitemMapper.updateByPrimaryKeySelective(cartitem);
		
		 Cartitem cartitems= cartitemMapper.selectByPrimaryKey(cartitem.getCartItemId());
	
			CartList cartList =new CartList();
			cartList.setCartItemId(cartitem.getCartItemId());
			cartList.setQuantity(cartitem.getQuantity());
			Book book = bookMapper.selectByPrimaryKey(cartitems.getBid());
			cartList.setBook(book);
			User user = userMapper.selectByPrimaryKey(cartitems.getUid());
			cartList.setUser(user);
			
		
		return cartList;
	}
	@Override
	public void deleteByprimarykey(String string) {
		cartitemMapper.deleteByPrimaryKey(string);
		
	}
	@Override
	public CartList selectByPrimarykey(String string) {
		 Cartitem cartitem= cartitemMapper.selectByPrimaryKey(string);
			
			CartList cartList =new CartList();
			cartList.setCartItemId(cartitem.getCartItemId());
			cartList.setQuantity(cartitem.getQuantity());
			Book book = bookMapper.selectByPrimaryKey(cartitem.getBid());
			cartList.setBook(book);
			User user = userMapper.selectByPrimaryKey(cartitem.getUid());
			cartList.setUser(user);
		return cartList;
	}

}
