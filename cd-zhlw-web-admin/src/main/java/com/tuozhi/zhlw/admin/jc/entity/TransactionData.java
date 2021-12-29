package com.tuozhi.zhlw.admin.jc.entity;

import java.io.Serializable;

import lombok.Data;

/** 
  * @author  作者: jxc
  * @date 创建时间：2020年7月13日 下午4:35:28 
  * @version 1.0 
*/
@Data
public class TransactionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pId;
	private String obuId;
	private String cardId;
	private String issuerId;
	private String issuerName;
	private String passId;
	private Integer owefee;


}
