package com.nuc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuc.pojo.ListCategory;
import com.nuc.service.CategoryService;

@Controller
@RequestMapping("/CategoryServlet")
public class CatergoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping("/findAll")
	public String findAll(Model model){
		List<ListCategory> parents = categoryService.findAll();
		model.addAttribute("parents", parents);
		return "left";
	}
}
