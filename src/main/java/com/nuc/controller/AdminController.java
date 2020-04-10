package com.nuc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nuc.pojo.Admin;
import com.nuc.service.AdminService;

@Controller
@RequestMapping("admin")
@SessionAttributes("admin")
public class AdminController {
@Autowired 
AdminService adminService;

	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(@RequestParam("adminname") String adminname,@RequestParam("adminpwd") String adminpwd,Model model){
		List<Admin> admins=adminService.login(adminname,adminpwd);
		if(admins.size()==0){
			model.addAttribute("msg", "用户名或密码错误！");
			return "/adminjsps/login.jsp";
		}
		model.addAttribute("admin", admins.get(0));
		return "redirect:/./adminjsps/admin/main.jsp";
	}

}
