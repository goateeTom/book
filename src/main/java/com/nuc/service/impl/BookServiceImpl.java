package com.nuc.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuc.mapper.BookMapper;
import com.nuc.mapper.CategoryMapper;
import com.nuc.mapper.SearchMapper;
import com.nuc.pojo.Book;
import com.nuc.pojo.BookExample;
import com.nuc.pojo.Category;
import com.nuc.pojo.ListCategory;
import com.nuc.pojo.PageBean;
import com.nuc.pojo.PageConstants;
import com.nuc.pojo.Search;
import com.nuc.pojo.SearchExample;
import com.nuc.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookMapper bookMapper;
	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	SearchMapper searchMapper;
	/**
	 * 加载图书
	 * @param bid
	 * @return
	 */
	public Book load(String bid) {
		Book book =bookMapper.selectByPrimaryKey(bid);
		Category category =categoryMapper.selectByPrimaryKey(book.getCid());
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
		book.setListCategory(listCategory);
		return book;
	}

	/**
	 * 返回当前分类下图书个数
	 * @param cid
	 * @return
	 */
	@Override
	public int findBookCountByCategory(String cid) {
		BookExample bookExample =new BookExample();
		BookExample.Criteria bookCriteria =bookExample.createCriteria();
		bookCriteria.andCidEqualTo(cid);
		
		return (int) bookMapper.countByExample(bookExample);
	}
	/**
	 * 按分类查
	 * @param cid
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findByCategory(String cid, int pc) {
		/*
		 * 1. 得到ps
		 * 2. 得到tr
		 * 3. 得到beanList
		 * 4. 创建PageBean，返回
		 */
		int ps = PageConstants.BOOK_PAGE_SIZE;
		int tr = findBookCountByCategory(cid);
		BookExample bookExample =new BookExample();
		bookExample.setStartRow((pc-1)*ps);
		bookExample.setPageSize(ps);
		BookExample.Criteria bookCriteria =bookExample.createCriteria();
		bookCriteria.andCidEqualTo(cid);
		List<Book> listBook= bookMapper.selectByExample(bookExample);
		PageBean<Book> pg = new PageBean<Book>();
		pg.setBeanList(listBook);
		pg.setPc(pc);
		pg.setPs(ps);
		pg.setTr(tr);
		return pg;
	}
	/**
	 * 按书名查
	 * @param bname
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findByBname(String bname, int pc,String uid) {
		int ps = PageConstants.BOOK_PAGE_SIZE;
		
		BookExample bookExample =new BookExample();
		bookExample.setStartRow((pc-1)*ps);
		bookExample.setPageSize(ps);
		BookExample.Criteria bookCriteria =bookExample.createCriteria();
		bookCriteria.andBnameLike("%"+bname+"%");
		int tr = (int) bookMapper.countByExample(bookExample);
		List<Book> listBook= bookMapper.selectByExample(bookExample);
		PageBean<Book> pg = new PageBean<Book>();
		pg.setBeanList(listBook);
		pg.setPc(pc);
		pg.setPs(ps);
		pg.setTr(tr);
		SearchExample example  = new SearchExample();
		SearchExample.Criteria criteria = example.createCriteria();
		criteria.andSnameEqualTo(bname);
		criteria.andUidEqualTo(uid);
		List<Search> searchs = searchMapper.selectByExample(example);
		if (searchs.size()==0) {
		Search search = new Search();
		search.setSname(bname);
		search.setSnum(1);
		search.setUid(uid);
		searchMapper.insert(search);
		}else {
			Search search = new Search();
			search.setSname(bname);
			search.setSnum(searchs.get(0).getSnum()+1);
			search.setUid(uid);
		searchMapper.updateByExampleSelective(search, example);
		}
		
		return pg;
	}
	/**
	 * 按作者查
	 * @param author
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findByAuthor(String author, int pc) {
		int ps = PageConstants.BOOK_PAGE_SIZE;
	
		BookExample bookExample =new BookExample();
		bookExample.setStartRow((pc-1)*ps);
		bookExample.setPageSize(ps);
		BookExample.Criteria bookCriteria =bookExample.createCriteria();
		bookCriteria.andAuthorLike("%"+author+"%");
		int tr = (int) bookMapper.countByExample(bookExample);
		List<Book> listBook= bookMapper.selectByExample(bookExample);
		PageBean<Book> pg = new PageBean<Book>();
		pg.setBeanList(listBook);
		pg.setPc(pc);
		pg.setPs(ps);
		pg.setTr(tr);
		return pg;
		
		
	}
	/**
	 * 按出版社查
	 * @param press
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findByPress(String press, int pc) {
		int ps = PageConstants.BOOK_PAGE_SIZE;
		
		BookExample bookExample =new BookExample();
		bookExample.setStartRow((pc-1)*ps);
		bookExample.setPageSize(ps);
		BookExample.Criteria bookCriteria =bookExample.createCriteria();
		bookCriteria.andPressLike("%"+press+"%");
		int tr = (int) bookMapper.countByExample(bookExample);
		List<Book> listBook= bookMapper.selectByExample(bookExample);
		PageBean<Book> pg = new PageBean<Book>();
		pg.setBeanList(listBook);
		pg.setPc(pc);
		pg.setPs(ps);
		pg.setTr(tr);
		return pg;
		
		
	}
	
	/**
	 * 多条件组合查询
	 * @param criteria
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findByCombination(Book criteria, int pc) {
		int ps = PageConstants.BOOK_PAGE_SIZE;
		
		BookExample bookExample =new BookExample();
		bookExample.setStartRow((pc-1)*ps);
		bookExample.setPageSize(ps);
		BookExample.Criteria bookCriteria =bookExample.createCriteria();
		if(criteria.getPress()!=null)
		bookCriteria.andPressLike("%"+criteria.getPress()+"%");
		if(criteria.getAuthor()!=null)
		bookCriteria.andAuthorLike("%"+criteria.getAuthor()+"%");
		if(criteria.getBname()!=null)
		bookCriteria.andBnameLike("%"+criteria.getBname()+"%");
		int tr = (int) bookMapper.countByExample(bookExample);
		List<Book> listBook= bookMapper.selectByExample(bookExample);
		PageBean<Book> pg = new PageBean<Book>();
		pg.setBeanList(listBook);
		pg.setPc(pc);
		pg.setPs(ps);
		pg.setTr(tr);
		return pg;
		
		
	}

	@Override
	public void add(Book book) {
		book.setBid(UUID.randomUUID().toString().replace("-", ""));
	bookMapper.insertSelective(book);
		
	}

	@Override
	public PageBean<Book> findNewBook() {
		int ps = PageConstants.BOOK_PAGE_SIZE;
		BookExample example =new BookExample();
		int tr = (int) bookMapper.countByExample(example);
		BookExample bookExample =new BookExample();
		BookExample.Criteria bookCriteria =bookExample.createCriteria();
		bookCriteria.andOrderbyGreaterThanOrEqualTo(89);
		List<Book> listBook= bookMapper.selectByExample(bookExample);
		PageBean<Book> pg = new PageBean<Book>();
		pg.setBeanList(listBook);
		pg.setPc(1);
		pg.setPs(ps);
		pg.setTr(12);
		return pg;
}

	@Override
	public PageBean<Book> recommend(String uid) {
		int  snum = searchMapper.recommend(uid);
		SearchExample searchExample = new SearchExample();
		SearchExample.Criteria criteria = searchExample.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andSnumEqualTo(snum);
		List<Search> searchs = searchMapper.selectByExample(searchExample);
		String bname = searchs.get(0).getSname();
		return findByBname(bname,1,uid);
	}
}
