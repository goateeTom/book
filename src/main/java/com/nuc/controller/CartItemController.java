package com.nuc.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nuc.pojo.Book;
import com.nuc.pojo.CartList;
import com.nuc.pojo.Cartitem;
import com.nuc.pojo.User;
import com.nuc.service.CartItemService;


@Controller
@RequestMapping("CartItemServlet")
@SessionAttributes({"sessionUser", "total","cartItemIds"})
public class CartItemController {
	@Autowired
	CartItemService cartItemService;

	
	@RequestMapping("myCart")
	public String myCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到uid
		 */
		User user = (User)req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		/*
		 * 2. 通过service得到当前用户的所有购物车条目
		 */
		List<CartList> cartItemLIst = cartItemService.myCart(uid);
		
		/*
		 * 3. 保存起来，转发到/cart/list.jsp
		 */
		req.setAttribute("cartItemList", cartItemLIst);
		return "forward:/jsp/cart/list.jsp";
	}
	/**
	 * 添加购物车条目
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(String bid, int quantity,HttpServletRequest req,HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到CartItem(bid, quantity)
		 */
		
		Cartitem cartitem =new Cartitem();
		cartitem.setBid(bid);
		cartitem.setQuantity(quantity);
		User user = (User)req.getSession().getAttribute("sessionUser");
		cartitem.setUid(user.getUid());
		/*
		 * 2. 调用service完成添加
		 */
		cartItemService.add(cartitem);
		/*
		 * 3. 查询出当前用户的所有条目，转发到list.jsp显示
		 */
		return myCart(req, resp);
	}
	@RequestMapping(value="updateQuantity",method=RequestMethod.POST)
	@ResponseBody
	public Result updateQuantity(@RequestParam("cartItemId")String cartItemId,@RequestParam("quantity")Integer quantity){
		 Cartitem cartitem =new Cartitem();
		 cartitem.setCartItemId(cartItemId);
		 cartitem.setQuantity(quantity);
		CartList cartList = cartItemService.updateQuantity(cartitem);
		Result result = new Result();
		result.setQuantity(cartList.getQuantity());
		result.setSubtotal(cartList.getSubtotal());
		return result;
		
	}
	
	@RequestMapping("batchDelete")
	public String batchDelete(String cartItemIds,HttpServletRequest req,HttpServletResponse resp)
			throws ServletException, IOException {
		String[] cartItemIdArray = cartItemIds.split(",");
		for (String string : cartItemIdArray) {
			cartItemService.deleteByprimarykey(string);
		}
		return myCart(req, resp);
	}
	@RequestMapping(value="loadCartItems",method=RequestMethod.POST)
	public String loadCartItems(@RequestParam("cartItemIds")String cartItemIds,@RequestParam("total")String hiddenTotal,Model model){
		String[] cartItemIdArray = cartItemIds.split(",");
		System.out.println(cartItemIds.isEmpty());
		System.out.println(hiddenTotal==null);
		//Double total = hiddenTotal;
		Double total = Double.parseDouble(hiddenTotal);
		List<CartList> cartLists = new ArrayList<CartList>();
		for (String string : cartItemIdArray) {
			CartList cartList = cartItemService.selectByPrimarykey(string);
			cartLists.add(cartList);
		}
		model.addAttribute("cartItemList", cartLists);
		model.addAttribute("total", total);
		model.addAttribute("cartItemIds", cartItemIds);
		return "cart/showitem";
	}
	
	private class Result {
		private int quantity;
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public double getSubtotal() {
			return subtotal;
		}
		public void setSubtotal(double subtotal) {
			this.subtotal = subtotal;
		}
		private double subtotal;
	}
	
}
