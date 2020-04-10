package com.nuc.service;

import java.util.List;

import com.nuc.pojo.User;

public interface UserService {

	User selectByPrimaryKey(String uid);

	User checkLogin(String loginname, String loginpass);

	boolean checkRegistName(String loginname);

	void doResgit(User user);

	boolean updatePassword(String uid, String newPass, String loginpass);

	boolean ajaxValidateEmail(String email);

	void activatioin(String code);


}
