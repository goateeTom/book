package com.nuc.service;

import java.util.List;

import com.nuc.pojo.Category;
import com.nuc.pojo.ListCategory;

public interface CategoryService {

	/**
	 * 查询指定父分类下子分类的个数
	 * @param pid
	 * @return
	 */
	public int findChildrenCountByParent(String pid) ;
	/**
	 * 删除分类
	 * @param cid
	 */
	public void delete(String cid);
	/**
	 * 修改分类
	 * @param category
	 */
	public void edit(ListCategory listCategory);
	/**
	 * 加载分类
	 * @param cid
	 * @return
	 */
	public ListCategory load(String cid);
	
	/**
	 * 添加分类
	 * @param category
	 */
	public void add(ListCategory listCategory);
	/**
	 * 查询所有分类
	 * @return
	 */
	public List<ListCategory> findAll() ;
	/**
	 * 获取所有父分类，不带子分类
	 * @return
	 */
	public List<ListCategory> findParents() ;
	/**
	 * 查询指定父分类下的所有子分类
	 * @param pid
	 * @return
	 */
	public List<ListCategory> findChildren(String pid);
}
