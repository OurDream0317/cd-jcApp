package com.tuozhi.zhlw.admin.jc.common;

/**
 * 	分页对象
 * @author jxc
 * @date 2020-01-05
 *  
 */
public class ConfigPager {

	private Integer page;

	private Integer limit;
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	public static Long getStartIndexBycondition(Integer page,Integer limit) {
		return (page-1)*limit*1L;
	}
	public static Long getEndIndexBycondition(Integer page,Integer limit) {
		return page*limit*1L;
	}
	
}

