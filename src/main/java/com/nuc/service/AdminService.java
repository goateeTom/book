package com.nuc.service;

import java.util.List;

import com.nuc.pojo.Admin;

public interface AdminService {

public	List<Admin> login(String adminname, String adminpwd);

}
