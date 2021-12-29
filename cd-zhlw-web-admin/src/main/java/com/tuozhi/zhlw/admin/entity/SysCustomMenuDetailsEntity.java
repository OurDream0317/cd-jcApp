package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author linqi
 * @create 2019/09/26 10:17
 **/
/**
 * @author tz008
 *
 */
@Data
@Table(name = "SYS_CUSTOM_MENU_DETAILS")
public class SysCustomMenuDetailsEntity implements Serializable {
    private static final long serialVersionUID = 6130421046505315922L;

    @Id
    @KeySql(sql = "select SEQ_SYS_CUSTMENU_DETAILS.nextval from dual", order = ORDER.BEFORE)
    private Long id;
    private Long customMenuId;
    private Long menuId;
    private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomMenuId() {
		return customMenuId;
	}
	public void setCustomMenuId(Long customMenuId) {
		this.customMenuId = customMenuId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
