package com.nuc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.inject.New;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuc.mapper.CategoryMapper;
import com.nuc.pojo.Category;
import com.nuc.pojo.CategoryExample;
import com.nuc.pojo.ListCategory;
import com.nuc.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	

	@Autowired CategoryMapper categoryMapper;
	/**
	 * 查询指定父分类下子分类的个数
	 * @param pid
	 * @return
	 */
	@Override
	public int findChildrenCountByParent(String pid) {
		CategoryExample categoryExample =new CategoryExample();
		 CategoryExample.Criteria categoryCriteria =categoryExample.createCriteria();

		categoryCriteria.andPidEqualTo(pid);
		
		return (int) categoryMapper.countByExample(categoryExample);
	}
	/**
	 * 删除分类
	 * @param cid
	 */
	@Override
	public void delete(String cid) {
		
		categoryMapper.deleteByPrimaryKey(cid);
	}
	
	/*
	 * 修改分类(non-Javadoc)
	 * @see com.nuc.service.CategoryService#edit(com.nuc.pojo.Category)
	 */
	@Override
	public void edit(ListCategory listCategory) {
		CategoryExample categoryExample =new CategoryExample();
		 CategoryExample.Criteria categoryCriteria =categoryExample.createCriteria();
		Category category =new Category();
		category.setCid(listCategory.getCid());
		category.setCname(listCategory.getCname());
		category.setDesc(listCategory.getDesc());
		if(listCategory.getParent()!=null)
		category.setPid(listCategory.getParent().getCid());
		categoryMapper.updateByPrimaryKeySelective(category);
	}

	/**
	 * 加载分类
	 * @param cid
	 * @return
	 */
	@Override
	public ListCategory load(String cid) {
		ListCategory listcategory = toListCategory(categoryMapper.selectByPrimaryKey(cid));
		
		return listcategory;
	}

	/*
	 * category转ListCategory
	 */
	private ListCategory toListCategory(Category category) {
		ListCategory listCategory = new ListCategory();
		listCategory.setCid(category.getCid());
		listCategory.setCname(category.getCname());
		listCategory.setDesc(category.getDesc());
		String pid =category.getPid();
		if(pid!=null){
			ListCategory parent = new ListCategory();
			parent.setCid(pid);
			listCategory.setParent(parent);
		}
		return listCategory;
	}
	@Override
	public void add(ListCategory listCategory) {
		CategoryExample categoryExample =new CategoryExample();
		 CategoryExample.Criteria categoryCriteria =categoryExample.createCriteria();
		Category category =new Category();
		category.setCid(listCategory.getCid());
		category.setCname(listCategory.getCname());
		category.setDesc(listCategory.getDesc());
		if(listCategory.getParent()!=null)
		category.setPid(listCategory.getParent().getCid());
		categoryMapper.insertSelective(category);
		
	}

	@Override
	public List<ListCategory> findAll() {
		List<ListCategory> parents = findParents();
		for (ListCategory parent : parents) {
			List<ListCategory> children = findChildren(parent.getCid());
			parent.setChildren(children);
			
		}
		return parents;
	}

	@Override
	public List<ListCategory> findParents() {
		CategoryExample categoryExample =new CategoryExample();
		 CategoryExample.Criteria categoryCriteria =categoryExample.createCriteria();
		categoryCriteria.andPidIsNull();
		 List<Category> categories = categoryMapper.selectByExample(categoryExample);
		   List<ListCategory> listCategories = new ArrayList<ListCategory>() ;
		   for (Category category : categories) {
			listCategories.add(toListCategory(category));
		}
					return listCategories;
	}

	@Override
	public List<ListCategory> findChildren(String pid) {
		CategoryExample categoryExample =new CategoryExample();
		 CategoryExample.Criteria categoryCriteria =categoryExample.createCriteria();
		categoryCriteria.andPidEqualTo(pid);
	   List<Category> categories = categoryMapper.selectByExample(categoryExample);
	   List<ListCategory> listCategories = new ArrayList<ListCategory>() ;
	   for (Category category : categories) {
		listCategories.add(toListCategory(category));
	}
				return listCategories;
	}

}
