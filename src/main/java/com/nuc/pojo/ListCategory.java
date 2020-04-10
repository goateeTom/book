package com.nuc.pojo;

import java.util.List;

public class ListCategory {
	private String cid;// 主键
	private String cname;// 分类名称
	private ListCategory parent;// 父分类
	private String desc;// 分类描述
	private List<ListCategory> children;// 子分类

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public ListCategory getParent() {
		return parent;
	}

	public void setParent(ListCategory parent) {
		this.parent = parent;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<ListCategory> getChildren() {
		return children;
	}

	public void setChildren(List<ListCategory> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "ListCategory [cid=" + cid + ", cname=" + cname + ", parent=" + parent + ", desc=" + desc + ", children="
				+ children + "]";
	}
}
