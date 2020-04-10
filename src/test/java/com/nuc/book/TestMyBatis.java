package com.nuc.book;



import static org.hamcrest.CoreMatchers.nullValue;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.nuc.pojo.User;
import com.nuc.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })

public class TestMyBatis {
    private static Logger logger = Logger.getLogger(TestMyBatis.class);
    // private ApplicationContext ac = null;
    @Resource
    private UserService userService = null;

    // @Before
    // public void before() {
    // ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    // userService = (IUserService) ac.getBean("userService");
    // }

    @Test
    public void test() {
        User user = userService.selectByPrimaryKey("x");
        // System.out.println(user.getUserName());
        // logger.info("值："+user.getUserName());
        logger.info(JSON.toJSONString(user));
    }
    @Test
    public void checkLogin(){
    	User user = userService.checkLogin("刘备", "123");
    	if(user==null){
    		System.out.println("nooooo");
    	}else {
    		logger.info(JSON.toJSONString(user));
		}
    }
}