package com.nuc.book;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nuc.pojo.CartList;
import com.nuc.pojo.Cartitem;
import com.nuc.service.CartItemService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestCartItem {
	 private static Logger logger = Logger.getLogger(TestMyBatis.class);
	 @Resource
	 CartItemService cartItemService;
	 
	 @Test
	 public void Testadd(){
		 Cartitem cartitem  = new Cartitem();
		 cartitem.setBid("CE01F15D435A4C51B0AD8202A318DCA7");
		 cartitem.setUid("xx");
		 cartitem.setQuantity(1);
		 cartItemService.add(cartitem);
	 }
	 @Test
	 public void TestMyCart(){
		 List<CartList> cartLists =cartItemService.myCart("xxx");
		 logger.info(cartLists.size());
	 }

	 
}
