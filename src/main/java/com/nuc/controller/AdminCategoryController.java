package com.nuc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuc.pojo.ListCategory;
import com.nuc.service.AdminService;
import com.nuc.service.BookService;
import com.nuc.service.CategoryService;

@Controller
@RequestMapping("admin/AdminCategory")
public class AdminCategoryController {
	@Autowired
	AdminService adminService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	BookService bookService;
	
	@RequestMapping("findAll")
	public String findAll(Model model){
		List<ListCategory> parents = categoryService.findAll();
		model.addAttribute("parents", parents);
		return "forward:/adminjsps/admin/category/list.jsp";
	}
	
	@RequestMapping("addParent")
	public String addParent(@RequestParam("cname")String cname,@RequestParam("desc")String desc,Model model ){
		ListCategory listCategory = new ListCategory();
		listCategory.setCid(UUID.randomUUID().toString().replace("-", ""));
		listCategory.setCname(cname);
		listCategory.setDesc(desc);
		categoryService.add(listCategory);
		return findAll(model);
	}
	
	@RequestMapping("addChildPre")
	public String addChildPre(@RequestParam("pid")String pid,Model model ){
		List<ListCategory> listCategories  = categoryService.findParents();
		model.addAttribute("pid", pid);
		model.addAttribute("parents",listCategories);
		return "forward:/adminjsps/admin/category/add2.jsp";
	}
	
	@RequestMapping("addChild")
	public String addChild(@RequestParam("pid")String pid,@RequestParam("cname")String cname,@RequestParam("desc")String desc,Model model ){
		ListCategory listCategory = new ListCategory();
		listCategory.setCid(UUID.randomUUID().toString().replace("-", ""));
		listCategory.setCname(cname);
		listCategory.setDesc(desc);
		ListCategory parent = new ListCategory();
		parent.setCid(pid);
		listCategory.setParent(parent);
		categoryService.add(listCategory);
		return findAll(model);
	}
	
	@RequestMapping("editParentPre")
	public String editParentPre(@RequestParam("cid")String cid,Model model){
		ListCategory listCategory = categoryService.load(cid);
		model.addAttribute("parent", listCategory);
		return "forward:/adminjsps/admin/category/edit.jsp";
	}
	
	@RequestMapping("editParent")
	public String editParent(@RequestParam("cid")String cid,@RequestParam("cname")String cname,@RequestParam("desc")String desc,Model model ){
		ListCategory parent = new ListCategory();
		parent.setCid(cid);
		parent.setCname(cname);
		parent.setDesc(desc);
		categoryService.edit(parent);
		return findAll(model);
	}
	
	@RequestMapping("editChildPre")
	public String editChildPre(@RequestParam("cid")String cid,Model model){
		model.addAttribute("child", categoryService.load(cid));
		model.addAttribute("parents", categoryService.findParents());
		return "forward:/adminjsps/admin/category/edit2.jsp";
	}
	
	@RequestMapping("editChild")
	public String editChild(@RequestParam("cid")String cid,@RequestParam("pid")String pid,@RequestParam("cname")String cname,@RequestParam("desc")String desc,Model model ){
		ListCategory listCategory = new ListCategory();
		listCategory.setCid(cid);
		listCategory.setCname(cname);
		listCategory.setDesc(desc);
		ListCategory parent = new ListCategory();
		parent.setCid(pid);
		listCategory.setParent(parent);
		categoryService.edit(listCategory);
		return findAll(model);
	}
	
	/**
	 * 删除一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("deleteParent")
	public String deleteParent(@RequestParam("cid")String cid ,Model model) {
		/*
		 * 1. 获取链接参数cid，它是一个1级分类的id
		 * 2. 通过cid，查看该父分类下子分类的个数
		 * 3. 如果大于零，说明还有子分类，不能删除。保存错误信息，转发到msg.jsp
		 * 4. 如果等于零，删除之，返回到list.jsp
		 */
	
		int cnt = categoryService.findChildrenCountByParent(cid);
		if(cnt > 0) {
			model.addAttribute("msg", "该分类下还有子分类，不能删除！");
			return "forward:/adminjsps/msg.jsp";
		} else {
			categoryService.delete(cid);
			return findAll(model);
		}
	}
	
	/**
	 * 删除2级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("deleteChild")
	public String deleteChild(@RequestParam("cid")String cid,Model model) {
		/*
		 * 1. 获取cid，即2级分类id
		 * 2. 获取该分类下的图书个数
		 * 3. 如果大于零，保存错误信息，转发到msg.jsp
		 * 4. 如果等于零，删除之，返回到list.jsp
		 */

		int cnt = bookService.findBookCountByCategory(cid);
		if(cnt > 0) {
			model.addAttribute("msg", "该分类下还存在图书，不能删除！");
			return "f:/adminjsps/msg.jsp";
		} else {
			categoryService.delete(cid);
			return findAll(model);
		}
	}
}

