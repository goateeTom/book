package com.nuc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuc.pojo.Order;
import com.nuc.pojo.PageBean;
import com.nuc.pojo.User;
import com.nuc.service.OrderService;
import com.nuc.service.UserService;
import com.nuc.util.SendMailUtil;

@Controller
@RequestMapping("admin/AdminOrder")
public class AdminOrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	UserService UserService;
	
	 @Autowired
	    private JavaMailSender javaMailSender;// 在spring中配置的邮件发送的bean
	/**
	 * 获取当前页码
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if(param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch(RuntimeException e) {}
		}
		return pc;
	}
	
	/**
	 * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
	 * @param req
	 * @return
	 */
	/*
	 * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=3
	 * /goods/BookServlet + methed=findByCategory&cid=xxx&pc=3
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
		int index = url.lastIndexOf("&pc=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}
	
	/**
	 * 查看所有订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findAll")
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService.findAll(pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "forward:/adminjsps/admin/order/list.jsp";
	}
	
	/**
	 * 按状态查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findByStatus")
	public String findByStatus(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取链接参数：status
		 */
		int status = Integer.parseInt(req.getParameter("status"));
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService.findByStatus(status, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "forward:/adminjsps/admin/order/list.jsp";
	}
	
	/**
	 * 查看订单详细信息
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
		return "forward:/adminjsps/admin/order/desc.jsp";
	}
	
	/**
	 * 取消订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
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
			return "forward:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);//设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已取消，您不后悔吗！");
		return "forward:/adminjsps/msg.jsp";		
	}
	
	/**
	 * 发货功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("deliver")
	public String deliver(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if(status != 2) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能发货！");
			return "forward:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 3);//设置状态为取消！
		 SendMailUtil sendMailUtil = new SendMailUtil();
		 Order order1 = orderService.load(oid);
		 User user1 = UserService.selectByPrimaryKey(order1.getUid());
		 sendMailUtil.sendMail(user1.getLoginname()+"您好:\n您的订单已发货，请注意查收。","网上购书网站发货消息", null, "13469247636@163.com", javaMailSender, true);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "该订单已发货！请等待确认收货");
		return "forward:/adminjsps/msg.jsp";		
	}
}
