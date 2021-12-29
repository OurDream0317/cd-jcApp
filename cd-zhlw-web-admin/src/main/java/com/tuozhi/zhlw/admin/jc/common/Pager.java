package com.tuozhi.zhlw.admin.jc.common;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 分页对象
 * @author LiHaoyuan
 * @date 2019年9月5日
 *  
 */
public class Pager {

	private int pageno;

	private int pagesize = 20;



	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	
}

