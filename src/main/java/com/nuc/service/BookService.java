package com.nuc.service;

import com.nuc.pojo.Book;
import com.nuc.pojo.PageBean;

public interface BookService {

	public Book load(String bid) ;
	public int findBookCountByCategory(String cid);
	public PageBean<Book> findByCategory(String cid, int pc);
	public PageBean<Book> findByBname(String bname, int pc, String uid) ;
	public PageBean<Book> findByAuthor(String author, int pc) ;
	public PageBean<Book> findByPress(String press, int pc);
	public PageBean<Book> findByCombination(Book criteria, int pc);
	public void add(Book book);
	public PageBean<Book> findNewBook();
	public PageBean<Book> recommend(String uid);
}
