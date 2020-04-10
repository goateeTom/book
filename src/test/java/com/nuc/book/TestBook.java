package com.nuc.book;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nuc.mapper.SearchMapper;
import com.nuc.pojo.Book;
import com.nuc.pojo.CartList;
import com.nuc.pojo.PageBean;
import com.nuc.pojo.Search;
import com.nuc.pojo.SearchExample;
import com.nuc.service.BookService;
import com.nuc.service.CartItemService;


	@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
	@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
	public class TestBook {
		 private static Logger logger = Logger.getLogger(TestMyBatis.class);
		   //  private ApplicationContext ac = null;
		    @Resource
		    BookService bookService;
		    @Resource
		    CartItemService cartItemService;
		    @Resource
		    SearchMapper searchMapper;
		    
		    @Test
		    public void TestLoadAndCount(){
		    	int a =bookService.findBookCountByCategory("5F79D0D246AD4216AC04E9C5FAB3199E");
		    	logger.info(a);
		    Book book= bookService.load("49D98E7916B94232862F7DCD1B0BAB66");
		    	logger.info(book.getBname());
		    	logger.info(book.getListCategory());
		    }
		    @Test
		    public void TestFindByCategory() {
		    	PageBean<Book> pg = bookService.findByCategory("5F79D0D246AD4216AC04E9C5FAB3199E", 2);
		    	List<Book> listBooks = pg.getBeanList();
		    	logger.info(listBooks.isEmpty());
		    	for (Book book : listBooks) {
		    		logger.info(book.getBname());
		    
					
				}
		    }
		    
		    @Test
		    public void TestFindByCombination() {
		    	Book book  =  new Book();
		    	book.setAuthor("孙卫琴");
		    	PageBean<Book> pg =	bookService.findByCombination(book, 1);
		    	List<Book> listBooks = pg.getBeanList();
		    	logger.info(listBooks.isEmpty());
		    	for (Book books : listBooks) {
		    		logger.info(books.getBname());
				}
		    }
		    
//		    @Test
//		    public void TestFindByLikeBname() {
//		    	PageBean<Book> pg =	bookService.findByBname("Java", 1);
//		    	
//		    	List<Book> listBooks = pg.getBeanList();
//		    	logger.info(listBooks.isEmpty());
//		    	for (Book books : listBooks) {
//		    		logger.info(books.getBname());
//				}
//		    }
		    @Test
		    public void TestFindme() {
		    	List<CartList> cartLists = new ArrayList<CartList>();
		    	cartLists = cartItemService.myCart("xxx");
		    	for (CartList cartList : cartLists) {
					logger.info(cartList.getBook().getImageB());
				}
		    	
		    	
		    }
		    
		    @Test
		    public void TestSearch() {
		    	int a = searchMapper.recommend("x");
		    	logger.info(a);
		    	SearchExample searchExample = new SearchExample();
				SearchExample.Criteria criteria = searchExample.createCriteria();
				criteria.andUidEqualTo("x");
				criteria.andSnumEqualTo(a);
				List<Search> searchs = searchMapper.selectByExample(searchExample);
				logger.info(searchs.get(0).getSname());
		    }
}
