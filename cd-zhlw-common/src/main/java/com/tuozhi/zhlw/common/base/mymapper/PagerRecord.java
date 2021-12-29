package com.tuozhi.zhlw.common.base.mymapper;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * @author LiHaoyuan
 * @date 2019年9月5日
 *  
 */
public class PagerRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String msg;
	private long count;
	private List<?> data;
	
	/** 
	 * 创建一个新的实例 PagerRecord.  
	 * 
	 */
	public PagerRecord(long count,List<?> data) {
		this.code = 0;
		this.count = count;
		this.data = data;
	}
	
	/** 
	 * 创建一个新的实例 PagerRecord.  
	 * 
	 */
	public PagerRecord(int code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}
	/**
	 * @return the data
	 */
	public List<?> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<?> data) {
		this.data = data;
	}
	
	
	
}


