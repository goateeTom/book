package com.nuc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nuc.pojo.Book;
import com.nuc.pojo.Category;
import com.nuc.pojo.ListCategory;
import com.nuc.pojo.PageBean;
import com.nuc.service.BookService;
import com.nuc.service.CategoryService;

@Controller
@RequestMapping("admin/AdminBook")
public class AdminBookController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	BookService bookService;
	
	@RequestMapping("addPre")
	public String addpre(Model model){
		List<ListCategory> listCategories =categoryService.findParents();
		model.addAttribute("parents", listCategories);
		return "forward:/adminjsps/admin/book/add.jsp";
	}
	
	@RequestMapping("ajaxFindChildren")
	@ResponseBody
	public List<ListCategory> ajaxFindChildren(@RequestParam("pid")String pid){
		List<ListCategory>listCategories=categoryService.findChildren(pid);
		return listCategories;
	}
	
	
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
	 * 按bid查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("load")
	public String load(String  bid,Model model) {
	
		Book book = bookService.load(bid);//通过bid得到book对象
		model.addAttribute("book", book);//保存到req中
		return "book/desc";//转发到desc.jsp
	}
	
	/**
	 * 按分类查
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findByCategory")
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String cid = req.getParameter("cid");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByCategory(cid, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/fo'r'ward:/adminjsps/admin/forward:/adminjsps/admin/book/list.jsp.jsp.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "forward:/adminjsps/admin/book/list.jsp";
	}
	
	/**
	 * 按作者查
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findByAuthor")
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String author = req.getParameter("author");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/forward:/adminjsps/admin/book/list.jsp.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "forward:/adminjsps/admin/book/list.jsp";
	}
	
	/**
	 * 按出版社查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findByPress")
	public String findByPress(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String press = req.getParameter("press");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/forward:/adminjsps/admin/book/list.jsp.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "forward:/adminjsps/admin/book/list.jsp";
	}
	
	/**
	 * 按图名查
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findByBname")
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String bname = req.getParameter("bname");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByBname(bname, pc,"a1");
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/forward:/adminjsps/admin/book/list.jsp.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "forward:/adminjsps/admin/book/list.jsp";
	}
	
	/**
	 * 多条件组合查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findByCombination")
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		Book criteria = new Book();
		if(!req.getParameter("bname").isEmpty())
		criteria.setBname(req.getParameter("bname"));
		if(!req.getParameter("author").isEmpty())
			criteria.setAuthor(req.getParameter("author"));
		if(!req.getParameter("press").isEmpty())
			criteria.setPress(req.getParameter("press"));
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/forward:/adminjsps/admin/book/list.jsp.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "forward:/adminjsps/admin/book/list.jsp";
	}
	
	@RequestMapping("findCategoryAll")
	public String findCategoryAll(Model model){
		List<ListCategory> parents = categoryService.findAll();
		model.addAttribute("parents", parents);
		return "forward:/adminjsps/admin/book/left.jsp";
	}
	
	
}
