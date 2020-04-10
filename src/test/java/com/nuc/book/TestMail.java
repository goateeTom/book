package com.nuc.book;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nuc.util.SendMailUtil;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestMail {
	 private static Logger logger = Logger.getLogger(TestMyBatis.class);
	 @Autowired
	    private JavaMailSender javaMailSender;// 在spring中配置的邮件发送的bean
	 @Test
	 public void testSend(){
		
		 //SendMailUtil sendMailUtil = new SendMailUtil();
		 //sendMailUtil.sendMail("激活邮件", "yes", null, "13469247636@163.com", javaMailSender, true);
		
			 logger.info(new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\book\\book_img").listFiles().length);
	
		 logger.info("不存在");
	 }
}
