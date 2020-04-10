package com.nuc.service.impl;

import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuc.mapper.AdminMapper;
import com.nuc.pojo.Admin;
import com.nuc.pojo.AdminExample;
import com.nuc.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminMapper adminMapper;

	@Override
	public List<Admin> login(String adminname, String adminpwd) {
		AdminExample adminExample = new AdminExample();
		AdminExample.Criteria adminCriteria = adminExample.createCriteria();
		adminCriteria.andAdminnameEqualTo(adminname);
		adminCriteria.andAdminpwdEqualTo(adminpwd);
		List<Admin> admins =adminMapper.selectByExample(adminExample);
		return admins;
	}

}
