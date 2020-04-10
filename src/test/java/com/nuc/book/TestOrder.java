package com.nuc.book;

import javax.annotation.Resource;
import javax.enterprise.inject.New;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nuc.pojo.Order;
import com.nuc.pojo.Orderitem;
import com.nuc.pojo.PageBean;
import com.nuc.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestOrder {
	  private static Logger logger = Logger.getLogger(TestMyBatis.class);
	  @Resource
	  OrderService orderService;
	  
	  @Test
	  public void TestMyOrder(){
		  PageBean<Order> pg = orderService.myOrders("xxx",1);
	  }
	  
}
