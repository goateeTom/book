package com.nuc.book;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nuc.pojo.ListCategory;
import com.nuc.service.CategoryService;
import com.nuc.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestCategory {
	 private static Logger logger = Logger.getLogger(TestMyBatis.class);
	    // private ApplicationContext ac = null;
	    @Resource
	    private CategoryService categoryService = null;
	@Test
	public void category1() {
		
		int a=categoryService.findChildrenCountByParent("1");
		logger.info(a);
	}
	@Test
	public void ListParent(){
		List<ListCategory> listCategories= new ArrayList<ListCategory>();
		listCategories = categoryService.findParents();
		for (ListCategory listCategory : listCategories) {
			logger.info(listCategory);
		}
	}
	@Test
	public void findAll(){
		List<ListCategory> listCategories= new ArrayList<ListCategory>();
		listCategories = categoryService.findAll();
		for (ListCategory listCategory : listCategories) {
			logger.info(listCategory);
		}
	}
	@Test
	public void findChildren() {
//		List<ListCategory> test= new ArrayList<ListCategory>();
//		test = categoryService.findChildren("1");
//		logger.info(test.size());
		List<ListCategory> listCategories= new ArrayList<ListCategory>();
		List<ListCategory> parents = categoryService.findParents();
		for (ListCategory parent : parents) {
			logger.info(parent.getCid());
		
		listCategories = categoryService.findChildren(parent.getCid());
		logger.info(listCategories.isEmpty());
		}
		
	}
	@Test
	public void findChildren2() {
		List<ListCategory> listCategories= new ArrayList<ListCategory>();
		listCategories  = categoryService.findChildren("1");
		logger.info(listCategories.size());
		for (ListCategory listCategory : listCategories) {
			logger.info(listCategory);
		}
	}
}
