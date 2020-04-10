package com.nuc.service;

import com.nuc.pojo.Order;
import com.nuc.pojo.PageBean;

public interface OrderService {

	void createOrder(Order order);

	PageBean<Order> myOrders(String uid, int pc);

	Order load(String oid);

	int findStatus(String oid);

	void updateStatus(String oid, int i);

	PageBean<Order> findAll(int pc);

	PageBean<Order> findByStatus(int status, int pc);

}
