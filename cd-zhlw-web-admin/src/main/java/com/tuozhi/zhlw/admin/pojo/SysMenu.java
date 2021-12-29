package com.tuozhi.zhlw.admin.pojo;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author linqi
 * @create 2019/09/06 10:06
 **/

@Data
public class SysMenu implements Serializable {

    @Id
    private Long id;
    private String functionName;
    private String functionCode;
    private String parentCode;
    private Integer leafNodeStatus;
    private String validStatus;
    private String orderIndex;
    private String description;
    private String systemGroup;
    private String iconcls;
    private Long appId;
    private String appName;
    private String url;
    private String sql;
    private Integer menuType;
    private String preDefineMenuId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public Integer getLeafNodeStatus() {
		return leafNodeStatus;
	}
	public void setLeafNodeStatus(Integer leafNodeStatus) {
		this.leafNodeStatus = leafNodeStatus;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public String getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSystemGroup() {
		return systemGroup;
	}
	public void setSystemGroup(String systemGroup) {
		this.systemGroup = systemGroup;
	}
	public String getIconcls() {
		return iconcls;
	}
	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Integer getMenuType() {
		return menuType;
	}
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
    
}
