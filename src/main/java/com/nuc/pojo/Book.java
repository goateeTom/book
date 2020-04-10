package com.nuc.pojo;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class Book {
    private String bid;

    private String bname;

    private String author;

    private BigDecimal price;

    private BigDecimal currprice;

    private BigDecimal discount;

    private String press;

    private String publishtime;

    private Integer edition;

    private Integer pagenum;

    private Integer wordnum;

    private String printtime;

    private Integer booksize;

    private String paper;

    private String cid;

    private String imageW;

    private String imageB;

    private Integer orderby;
    
    private MultipartFile file;
    
    public ListCategory getListCategory() {
		return listCategory;
	}

	public void setListCategory(ListCategory listCategory) {
		this.listCategory = listCategory;
	}

	private ListCategory listCategory;

   

	public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid == null ? null : bid.trim();
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname == null ? null : bname.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCurrprice() {
        return currprice;
    }

    public void setCurrprice(BigDecimal currprice) {
        this.currprice = currprice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press == null ? null : press.trim();
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime == null ? null : publishtime.trim();
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public Integer getWordnum() {
        return wordnum;
    }

    public void setWordnum(Integer wordnum) {
        this.wordnum = wordnum;
    }

    public String getPrinttime() {
        return printtime;
    }

    public void setPrinttime(String printtime) {
        this.printtime = printtime == null ? null : printtime.trim();
    }

    public Integer getBooksize() {
        return booksize;
    }

    public void setBooksize(Integer booksize) {
        this.booksize = booksize;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper == null ? null : paper.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getImageW() {
        return imageW;
    }

    public void setImageW(String imageW) {
        this.imageW = imageW == null ? null : imageW.trim();
    }

    public String getImageB() {
        return imageB;
    }

    public void setImageB(String imageB) {
        this.imageB = imageB == null ? null : imageB.trim();
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}