package com.nuc.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.config.AlipayConfig;
import com.nuc.pojo.CartList;
import com.nuc.pojo.Order;
import com.nuc.pojo.Orderitem;
import com.nuc.pojo.PageBean;
import com.nuc.pojo.User;
import com.nuc.service.CartItemService;
import com.nuc.service.OrderService;

@Controller
@RequestMapping("OrderServlet")
public class OrderController {
	final static Logger log = Logger.getLogger(OrderController.class);
	@Autowired
	OrderService orderService;
	@Autowired
	CartItemService  cartItemService;

	@RequestMapping("createOrder")
	public String createOrder(HttpServletRequest req, HttpServletResponse resp){
		String cartItemIds= req.getParameter("cartItemIds");
		List<CartList> cartLists =new ArrayList<CartList>();
		String[] strings = cartItemIds.split(",");
		for (String string : strings) {
			cartLists.add(cartItemService.selectByPrimarykey(string));
		}
		/*
		 * 创建订单Order
		 */
		Order order = new Order();
		order.setOid(UUID.randomUUID().toString().replace("-", ""));//设置主键
		order.setOrdertime(String.format("%tF %<tT", new Date()));//下单时间
		order.setStatus(1);//设置状态，1表示未付款
		order.setAddress(req.getParameter("address"));//设置收货地址
		User owner = (User)req.getSession().getAttribute("sessionUser");
		order.setUser(owner);
		order.setUid(owner.getUid());
		
		BigDecimal total = new BigDecimal("0");
		for(CartList cartItem : cartLists) {
			total = total.add(new BigDecimal(cartItem.getSubtotal() + ""));
		}
		order.setTotal(total);//设置总计
		
		/*
		 * 3. 创建List<OrderItem>
		 * 一个CartItem对应一个OrderItem
		 */
		List<Orderitem> orderItemList = new ArrayList<Orderitem>();
		for(CartList cartItem : cartLists) {
			Orderitem orderItem = new Orderitem();
			orderItem.setOrderItemId(UUID.randomUUID().toString().replace("-", ""));//设置主键
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(new BigDecimal (cartItem.getSubtotal()+""));
			orderItem.setBid(cartItem.getBook().getBid());
			orderItem.setBname(cartItem.getBook().getBname());
			orderItem.setCurrPrice(cartItem.getBook().getCurrprice());
			orderItem.setImage_b(cartItem.getBook().getImageB());
			orderItem.setOrder(order);
			orderItem.setOid(order.getOid());
			orderItemList.add(orderItem);
		}
		order.setOrderItemList(orderItemList);
		
		/*
		 * 4. 调用service完成添加
		 */
		orderService.createOrder(order);
		
		// 删除购物车条目
		for (CartList cartList : cartLists) {
		cartItemService.deleteByprimarykey(cartList.getCartItemId());
		}
		/*
		 * 5. 保存订单，转发到ordersucc.jsp
		 */
		req.setAttribute("order", order);
		return "order/ordersucc";
	}
	/**
	 * 我的订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("myOrders")
	public String myOrders(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = 1;
		String param = req.getParameter("pc");
		if(param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch(RuntimeException e) {}
		}
		/*
		 * 2. 得到url：...
		 */
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
		int index = url.lastIndexOf("&pc=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		/*
		 * 3. 从当前session中获取User
		 */
		User user = (User)req.getSession().getAttribute("sessionUser");
		
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "order/list";
	}
	
	/**
	 * 加载订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("load")
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		String btn = req.getParameter("btn");//btn说明了用户点击哪个超链接来访问本方法的
		req.setAttribute("btn", btn);
		return "order/desc";
	}
	
	//取消订单
	@RequestMapping("cancel")
	public String cancel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if(status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消！");
			return "msg";
		}
		orderService.updateStatus(oid, 5);//设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已成功取消！");
		return "msg";		
	}
	/**
	 * 确认收货
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("confirm")
	public String confirm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if(status != 3) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能确认收货！");
			return "msg";
		}
		orderService.updateStatus(oid, 4);//设置状态为交易成功！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "恭喜，交易成功！");
		return "msg";		
	}
	
	@RequestMapping(value="paymentPre", produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String paymentPre(String oid,HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AlipayApiException {
				Order order  = orderService.load(oid);
				List<Orderitem> orderitems = order.getOrderItemList();
				  //获得初始化的AlipayClient
		        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

		        //设置请求参数
		        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		        alipayRequest.setReturnUrl(AlipayConfig.return_url);
		        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

		        //商户订单号，商户网站订单系统中唯一订单号，必填
		        String out_trade_no = oid;
		        //付款金额，必填
		        String total_amount = order.getTotal()+"";
		        //订单名称，必填
		        String subject = orderitems.get(0).getBname();
		        //商品描述，可空
		        String body = "用户订购商品个数：" + orderitems.get(0).getQuantity();

		        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。该参数数值不接受小数点， 如 1.5h，可转换为 90m。
		        String timeout_express = "1c";

		        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
						+ "\"total_amount\":\""+ total_amount +"\","
						+ "\"subject\":\""+ subject +"\","
						+ "\"body\":\""+ body +"\","
						+ "\"timeout_express\":\""+ timeout_express +"\","
						+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


		        //请求
		        String result = alipayClient.pageExecute(alipayRequest).getBody();

				return result;
		
	}
	
	@RequestMapping(value = "/alipayReturnNotice")
	public ModelAndView alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
 
		log.info("支付成功, 进入同步通知接口...");
 
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
 
		boolean signVerified = false;
		try{
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
		}catch (Exception e) {
			System.out.println("SDK验证签名出现异常");
		}
 
		ModelAndView mv = new ModelAndView("msg");
		mv.addObject("code", "success");
		mv.addObject("msg", "恭喜，交易成功");
		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
 
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
 
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
 
			// 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
			orderService.updateStatus(out_trade_no, 2);
//			orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
// 
// 
//			Orders order = orderService.getOrderById(out_trade_no);
//			Product product = productService.getProductById(order.getProductId());
// 
			log.info("********************** 支付成功(支付宝同步通知) **********************");
    		log.info("* 订单号: {}"); log.info(out_trade_no);
    		log.info("* 支付宝交易号: {}"); log.info(trade_no);
    		log.info("* 实付金额: {}"); log.info(total_amount);
  //  		log.info("* 购买产品: {}"); log.info(product.getName());
    		log.info("***************************************************************");
 
 
    		mv.addObject("out_trade_no", out_trade_no);
    		mv.addObject("trade_no", trade_no);
    		mv.addObject("total_amount", total_amount);
    //		mv.addObject("productName", product.getName());
 
		}else {
			log.info("支付, 验签失败...");
		}
 
		return mv;
	}
 
	/**
	 * @Description: 支付宝异步 通知页面
	 */
	@RequestMapping(value = "/alipayNotifyNotice")
	@ResponseBody
	public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
 
		log.info("支付成功, 进入异步通知接口...");
 
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
 
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
 
		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
 
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
 
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
 
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
 
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
 
				//注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			}else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
 
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
 
				// 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
//				orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
// 
//				Orders order = orderService.getOrderById(out_trade_no);
//				Product product = productService.getProductById(order.getProductId());
// 
				log.info("********************** 支付成功(支付宝异步通知) **********************");
	    		log.info("* 订单号: {}"); log.info(out_trade_no);
	    		log.info("* 支付宝交易号: {}"); log.info(trade_no);
	    		log.info("* 实付金额: {}"); log.info(total_amount);
//	    		log.info("* 购买产品: {}", product.getName());
	    		log.info("***************************************************************");
			}
			log.info("支付成功...");
 
		}else {//验证失败
			log.info("支付, 验签失败...");
		}
 
		return "success";
	}


}
