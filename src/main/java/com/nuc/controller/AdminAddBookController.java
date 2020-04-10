package com.nuc.controller;



import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nuc.pojo.Book;
import com.nuc.service.BookService;

@Controller
@RequestMapping("admin/AdminAddBook")
public class AdminAddBookController {
	@Autowired
	BookService bookService;
	@RequestMapping("/upload")
	public String upload(Book book,HttpServletRequest request,Model model) throws Exception{
	  //保存数据库的路径
	  String sqlPath = null; 
	  //定义文件保存的本地路径
      String localPath="C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\book\\book_img\\";
      //定义 文件名
      String filename=null;  
      if(!book.getFile().isEmpty()){  
          //生成uuid作为文件名称  
          String uuid = UUID.randomUUID().toString().replaceAll("-","");  
          //获得文件类型（可以判断如果不是图片，禁止上传）  
          String contentType=book.getFile().getContentType();  
          //获得文件后缀名 
          String suffixName=contentType.substring(contentType.indexOf("/")+1);
          //得到 文件名
          filename=uuid+"."+suffixName; 
          //文件保存路径
          book.getFile().transferTo(new File(localPath+filename));  
      }
      //把图片的相对路径保存至数据库
      sqlPath = "/book_img/"+filename;
      System.out.println(sqlPath);
      book.setImageB(sqlPath);
      bookService.add(book);
      model.addAttribute("Book", book);
	  return "book/list";
	}

}
