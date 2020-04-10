package com.nuc.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nuc.mapper.UserMapper;
import com.nuc.pojo.User;
import com.nuc.pojo.UserExample;
import com.nuc.service.UserService;
import com.nuc.util.SendMailUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private JavaMailSender javaMailSender;// 在spring中配置的邮件发送的bean
	@Autowired
	UserMapper userMapper;

	@Override
public User selectByPrimaryKey(String uid) {
		
		return userMapper.selectByPrimaryKey(uid);
	}

	@Override
	public User checkLogin(String loginname, String loginpass) {
		UserExample example =new UserExample();
		UserExample.Criteria criteria =example.createCriteria();
		criteria.andLoginnameEqualTo(loginname);
		criteria.andLoginpassEqualTo(loginpass);
		List<User> user =  userMapper.selectByExample(example);
		return user.get(0);
	}

	@Override
	public boolean checkRegistName(String loginname) {
		UserExample example =new UserExample();
		UserExample.Criteria criteria =example.createCriteria();
		criteria.andLoginnameEqualTo(loginname);
		List<User> user = userMapper.selectByExample(example);
		return user.isEmpty();
		
	}

	@Override
	public void doResgit(User user) {
		user.setUid(UUID.randomUUID().toString().replace("-", ""));
		user.setStatus(false);
		String activationcode =UUID.randomUUID().toString().replace("-", "")+UUID.randomUUID().toString().replace("-", "");
		user.setActivationcode(activationcode);
		 SendMailUtil sendMailUtil = new SendMailUtil();
		 sendMailUtil.sendMail( 
				"<html>"
				+ "<body>"
				+ "<p>"
				+ "<a href=\"http://192.168.137.1:8080/book/user/activation?activationCode=&#34"+activationcode
				+ "&#34\">购书网站激活</a>请点击该链接地址激活您的账户</p>"
+"</body>"
+"</html>"

				,"购书网站注册激活",  null, "13469247636@163.com", javaMailSender, true);
		userMapper.insertSelective(user);
		
	}

	@Override
	public boolean updatePassword(String uid, String newPass, String loginpass) {
		User user =userMapper.selectByPrimaryKey(uid);
		if(user.getLoginpass()!=loginpass) {
			return false;
		}else{
			user.setLoginpass(newPass);
			userMapper.updateByPrimaryKey(user);
			return true;
		}
			
	}

	@Override
	public boolean ajaxValidateEmail(String email) {
		UserExample example =new UserExample();
		UserExample.Criteria criteria =example.createCriteria();
		criteria.andEmailEqualTo(email);
		List<User> user = userMapper.selectByExample(example);
		return user.isEmpty();
	}

	@Override
	public void activatioin(String code) {
		UserExample example =new UserExample();
		UserExample.Criteria criteria =example.createCriteria();
		criteria.andActivationcodeEqualTo(code);
		User user = new User();
		user.setStatus(true);
		userMapper.updateByExampleSelective(user, example);
		
	}

	
	
	

}
