package com.nuc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuc.mapper.OrderMapper;
import com.nuc.mapper.OrderitemMapper;
import com.nuc.pojo.Order;
import com.nuc.pojo.OrderExample;
import com.nuc.pojo.Orderitem;
import com.nuc.pojo.OrderitemExample;
import com.nuc.pojo.PageBean;
import com.nuc.pojo.PageConstants;
import com.nuc.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderitemMapper orderitemMapper;
	
	@Override
	public void createOrder(Order order) {
		orderMapper.insert(order);
		List<Orderitem> orderitems = order.getOrderItemList();
		for (Orderitem orderitem : orderitems) {
			orderitemMapper.insert(orderitem);
		}
	}

	@Override
	public PageBean<Order> myOrders(String uid, int pc) {
		int ps = PageConstants.BOOK_PAGE_SIZE;
		OrderExample orderExample =new OrderExample();
		orderExample.setStartRow((pc-1)*ps);
		orderExample.setPageSize(ps);
		OrderExample.Criteria criteria = orderExample.createCriteria();
		criteria.andUidEqualTo(uid);
		 int tr = (int)orderMapper.countByExample(orderExample);
		 List<Order> orders = orderMapper.selectByExample(orderExample);
		 for (Order order2 : orders) {
			
		 OrderitemExample orderitemExample = new OrderitemExample();
		 OrderitemExample.Criteria orderitemCriteria = orderitemExample.createCriteria();
		 orderitemCriteria.andOidEqualTo(order2.getOid());
		 List<Orderitem> orderitems= orderitemMapper.selectByExample(orderitemExample);
		 order2.setOrderItemList(orderitems);
		 }
		 PageBean<Order> orderPageBean = new PageBean<Order>();
		 orderPageBean.setBeanList(orders);
		 orderPageBean.setPc(pc);
		 orderPageBean.setPs(ps);
		 orderPageBean.setTr(tr);
		return orderPageBean;
	}

	@Override
	public Order load(String oid) {
	Order order  =	orderMapper.selectByPrimaryKey(oid);
	 OrderitemExample orderitemExample = new OrderitemExample();
	 OrderitemExample.Criteria orderitemCriteria = orderitemExample.createCriteria();
	 orderitemCriteria.andOidEqualTo(order.getOid());
	 List<Orderitem> orderitems= orderitemMapper.selectByExample(orderitemExample);
	 order.setOrderItemList(orderitems);
		return order;
	}

	@Override
	public int findStatus(String oid) {
		Order order = orderMapper.selectByPrimaryKey(oid);
		int Status =order.getStatus();
		return Status;
	}

	@Override
	public void updateStatus(String oid, int i) {
		Order order = new Order();
		order.setStatus(i);
		order.setOid(oid);
		orderMapper.updateByPrimaryKeySelective(order);
	}

	@Override
	public PageBean<Order> findAll(int pc) {
		int ps = PageConstants.ORDER_PAGE_SIZE;
		OrderExample orderExample =new OrderExample();
		orderExample.setStartRow((pc-1)*ps);
		orderExample.setPageSize(ps);
		OrderExample.Criteria criteria = orderExample.createCriteria();
		 int tr = (int)orderMapper.countByExample(orderExample);
		 List<Order> orders = orderMapper.selectByExample(orderExample);
		 for (Order order2 : orders) {
			
		 OrderitemExample orderitemExample = new OrderitemExample();
		 OrderitemExample.Criteria orderitemCriteria = orderitemExample.createCriteria();
		 orderitemCriteria.andOidEqualTo(order2.getOid());
		 List<Orderitem> orderitems= orderitemMapper.selectByExample(orderitemExample);
		 order2.setOrderItemList(orderitems);
		 }
		 PageBean<Order> orderPageBean = new PageBean<Order>();
		 orderPageBean.setBeanList(orders);
		 orderPageBean.setPc(pc);
		 orderPageBean.setPs(ps);
		 orderPageBean.setTr(tr);
		return orderPageBean;
	
	}

	@Override
	public PageBean<Order> findByStatus(int status, int pc) {
		int ps = PageConstants.ORDER_PAGE_SIZE;
		OrderExample orderExample =new OrderExample();
		orderExample.setStartRow((pc-1)*ps);
		orderExample.setPageSize(ps);
		OrderExample.Criteria criteria = orderExample.createCriteria();
		criteria.andStatusEqualTo(status);
		 int tr = (int)orderMapper.countByExample(orderExample);
		 List<Order> orders = orderMapper.selectByExample(orderExample);
		 for (Order order2 : orders) {
			
		 OrderitemExample orderitemExample = new OrderitemExample();
		 OrderitemExample.Criteria orderitemCriteria = orderitemExample.createCriteria();
		 orderitemCriteria.andOidEqualTo(order2.getOid());
		 criteria.andStatusEqualTo(status);
		 List<Orderitem> orderitems= orderitemMapper.selectByExample(orderitemExample);
		 order2.setOrderItemList(orderitems);
		 }
		 PageBean<Order> orderPageBean = new PageBean<Order>();
		 orderPageBean.setBeanList(orders);
		 orderPageBean.setPc(pc);
		 orderPageBean.setPs(ps);
		 orderPageBean.setTr(tr);
		return orderPageBean;

	}

}
