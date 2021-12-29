package com.tuozhi.zhlw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.tuozhi.zhlw.admin.dao.SysMenuDao;
import com.tuozhi.zhlw.admin.entity.SysMenuEntity;
import com.tuozhi.zhlw.admin.entity.SysRolesMenuEntity;
import com.tuozhi.zhlw.admin.entity.SysRolesMenuPK;
import com.tuozhi.zhlw.admin.mapper.CustomMenuDetailMapper;
import com.tuozhi.zhlw.admin.mapper.MenuMapper;
import com.tuozhi.zhlw.admin.pojo.SysMenu;
import com.tuozhi.zhlw.admin.service.SysMenuService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/03 17:30
 **/

@Service
public class SysMenuServiceImpl extends AbstractMapperServiceImpl<MenuMapper, SysMenuEntity> implements SysMenuService {

	@Autowired
	SysMenuDao sysMenuDao;

	@Resource
	MenuMapper menuMapper;

	@Resource
	CustomMenuDetailMapper customMenuDetailMapper;

	// admin角色
	private static final String adminRole = "1";

	@Override
	public List<SysMenu> findAllByUserId(Long userId) {
		return menuMapper.findAllByUserId(userId);
	}

	@Override
	public List<SysMenu> findAllMenu() {
		return menuMapper.findAllMenu();
	}

	@Override
	public List<SysMenu> findAllMenuByRoleId(String roleId) {
		return menuMapper.findAllMenuByRoleId(roleId);
	}

	@Override
	public List<SysMenu> findAllMenuByAppId(Long appId) {
		return menuMapper.findAppAllMenus(appId);
	}

	/**
	 * 创建前端TreeNode app>fucntion code > child
	 *
	 * @param sysMenuList
	 * @return
	 */
	@Override
	public List<Object> createMenuTreeNode(String parentCode, List<SysMenu> sysMenuList, String checkFlag,Boolean expandedFlag) {

		List<Object> objList = new ArrayList<Object>();
		for (SysMenu sysMenu : sysMenuList) {
			if (sysMenu.getParentCode().equals(parentCode)) {
				Map<String, Object> objMap = new HashMap<>();
				// if (sysMenu.getLeafNodeStatus() == 0) {
				String description = "";
				if(sysMenu.getDescription()!=null) {
					description = sysMenu.getDescription();
				}
				objMap.put("id", sysMenu.getFunctionCode());
				objMap.put("parentId", sysMenu.getParentCode());
				objMap.put("parentName", sysMenu.getParentCode());
				objMap.put("title", sysMenu.getFunctionName());
				objMap.put("text", "<span title='"+description+"'>"+sysMenu.getFunctionName()+"</span>");
				objMap.put("qtip", sysMenu.getDescription());
				objMap.put("orderindex", sysMenu.getOrderIndex());
				objMap.put("leaf", false);
				objMap.put("isLeaf", sysMenu.getLeafNodeStatus());
				objMap.put("viewType", "menu" + sysMenu.getFunctionCode());
				objMap.put("routeId", "menu" + sysMenu.getFunctionCode());
				objMap.put("iconCls", sysMenu.getIconcls());
				objMap.put("rowCls", "nav-tree-badge nav-tree-badge-new");
				objMap.put("menuType", sysMenu.getMenuType());
				objMap.put("url", sysMenu.getUrl());
				objMap.put("sql", sysMenu.getUrl());
				objMap.put("preDefineMenuId", sysMenu.getPreDefineMenuId());

				if (!checkFlag.equals("")) {
					objMap.put("checked", false);
				}

				List<Object> mList = createMenuTreeNode(sysMenu.getFunctionCode(), sysMenuList, checkFlag, expandedFlag);
				if (mList.size() > 0) {
					objMap.put("children", mList);
					objMap.put("expanded", expandedFlag);
				} else {
					objMap.remove("leaf");
					objMap.put("leaf", "true");
				}
				objList.add(objMap);

			}

		}

		return objList;
	}

	@Override
	public List<Object> getSysMenuTreeCrecursion(long parentId, List funcList, String fram) {
		List<Object> objList = new ArrayList<Object>();
		for (int i = 0; i < funcList.size(); i++) {
			Map<String, Object> objMap = new HashMap<String, Object>();
			HashMap<String, Object> current = (HashMap<String, Object>) funcList.get(i);
			String PARENTID = String.valueOf(current.get("PARENTID"));
			String FUNCTIONID = String.valueOf(current.get("FUNCTIONID"));
			String ICONCLS = String.valueOf(current.get("ICONCLS"));
			String ISSQLFUNCTION = String.valueOf(current.get("ISSQLFUNCTION"));
			String AUTOLOADDATA = "0";
			if (current.get("AUTOLOADDATA") != null) {
				AUTOLOADDATA = String.valueOf(current.get("AUTOLOADDATA"));
			}

			int ISLEAFNODE = 0;
			if (current.get("ISLEAFNODE") == null) {
				ISLEAFNODE = 0;
			} else if (String.valueOf(current.get("ISLEAFNODE")) == "false") {
				ISLEAFNODE = 0;
			} else if (String.valueOf(current.get("ISLEAFNODE")) == "true") {
				ISLEAFNODE = 1;
			} else {
				ISLEAFNODE = Integer.parseInt(String.valueOf(current.get("ISLEAFNODE")));
			}
			long functionId = Long.parseLong(FUNCTIONID);
			long pId = Long.parseLong(PARENTID);
			String dataSource = "";
			if (current.get("DATASOURCE") != null && current.get("DATASOURCE").toString() != "") {
				dataSource = current.get("DATASOURCE").toString();
			}
			if (pId == parentId) {
				objMap.put("id", FUNCTIONID);
				objMap.put("parentId", pId);
				if (pId == (long) 0) {
					objMap.put("parentName", "根节点");
				} else {
					objMap.put("parentName", current.get("PARENTNAME"));
				}
				if (ISSQLFUNCTION.equals("1")) {
					objMap.put("issqlfunction", "是");
				} else {
					objMap.put("issqlfunction", "否");
				}
				objMap.put("text", current.get("FUNCTIONNAME"));
				objMap.put("qtip", current.get("DESCRIPTION"));
				objMap.put("orderindex", current.get("ORDERINDEX"));
				objMap.put("leaf", false);
				objMap.put("expanderOnly", true);
				objMap.put("isLeaf", ISLEAFNODE);
				objMap.put("autoLoadData", AUTOLOADDATA);
				objMap.put("viewType", "menu" + FUNCTIONID);
				objMap.put("routeId", "menu" + FUNCTIONID);
				objMap.put("iconCls", ICONCLS);
				objMap.put("rowCls", "nav-tree-badge nav-tree-badge-new");
				objMap.put("isSqlFunction", current.get("ISSQLFUNCTION"));
				String sql = StringEscapeUtils.unescapeJava(current.get("SQLSCRIPT").toString());
				sql = sql.substring(1, sql.length() - 1).trim();
				objMap.put("sqlScript", sql);
				objMap.put("dataSource", dataSource);
				objMap.put("primaryKey", current.get("PRIMARYKEY"));
				if (!fram.equals("")) {
					objMap.put("checked", false);
				}
				List<Object> preList = getSysMenuTreeCrecursion(functionId, funcList, fram);
				if (preList.size() > 0) {
					objMap.put("children", preList);
					objMap.put("expanded", false);
					objMap.put("expanderOnly", true);
				} else {
					objMap.remove("leaf");
					objMap.put("leaf", "true");
				}
				objList.add(objMap);
			}
		}
		return objList;
	}

	@Override
	@Transactional
	public void saveMenuAndRolesMenu(SysMenuEntity menuEntity, String[] roleIds) {

		saveNotNull(menuEntity);
		SysRolesMenuPK sysRolesMenuPKAdmin = new SysRolesMenuPK();
		sysRolesMenuPKAdmin.setRoleId(Long.valueOf(adminRole));
		SysRolesMenuEntity sysRolesMenuAdmin = new SysRolesMenuEntity();
		sysRolesMenuAdmin.setSysRolesMenuPK(sysRolesMenuPKAdmin);
		sysRolesMenuAdmin.getSysRolesMenuPK().setFunctionId(menuEntity.getId());
		sysMenuDao.saveEntity(sysRolesMenuAdmin);
		/*
		 * for (String roleId : roleIds) { if(adminRole.equals(roleId)) { continue; }
		 * 
		 * SysRolesMenuPK sysRolesMenuPK = new SysRolesMenuPK();
		 * sysRolesMenuPK.setRoleId(Long.valueOf(roleId)); SysRolesMenuEntity
		 * sysRolesMenu = new SysRolesMenuEntity();
		 * sysRolesMenu.setSysRolesMenuPK(sysRolesMenuPK);
		 * sysRolesMenu.getSysRolesMenuPK().setFunctionId(menuEntity.getId());
		 * 
		 * sysMenuDao.saveEntity(sysRolesMenu);
		 * 
		 * }
		 */

	}

	/**
	 * 菜单删除，删除当前菜单及之后所有后代菜单
	 * 
	 * @param functionCode 菜单编号
	 */
	@Override
	@Transactional
	public void deleteMenuByFunction(String functionCode) {
		this.menuMapper.queryMenusCodeByParentCode(functionCode).forEach((childCode) -> {
			this.deleteMenuByFunction(childCode); // 往下走 从最下边的菜单开始删
			this.deleteMenuAction(childCode);
		});
		// 把最顶层的菜单删了
		this.deleteMenuAction(functionCode);
	}

	/**
	 * 执行单个菜单的具体删除操作 1. 更新此菜单删除标志位 2. 删除菜单-角色的关联关系 3. 删除自定义菜单详情中的相关引用
	 * 
	 * @param functionCode 当前菜单编码
	 */
	private void deleteMenuAction(String functionCode) {
		this.sysMenuDao.deleteByFunctionCode(functionCode);
		this.mapper.deleteRolesMenuByFunctionCode(functionCode);
		this.customMenuDetailMapper.deleteByMenuIdWithCode(functionCode);
	}

	@Override
	public List<SysMenu> getSysMenuTreeIsNotLeafNode(Long appId) {
		return mapper.getSysMenuTreeIsNotLeafNode(appId);
	}

	@Override
	public List<Map<String, Object>> getPredefineMenuTreeByUserId(Map map) throws IOException, SQLException {
		List<Map<String, Object>> list = mapper.getPredefineMenuTreeByUserId(map);
		for (Map map1 : list) {
			byte[] bytes = ClobToString((Clob) map1.get("SQLSCRIPT")).getBytes();
			String str = JSON.toJSONString(new String(bytes, "UTF-8"));
			map1.put("SQLSCRIPT", str);
		}
		return list;
	}

	// Clob类型 转String
	public String ClobToString(Clob clob) throws SQLException, IOException {
		String ret = "";
		Reader read = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(read);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {
			sb.append(s);
			s = br.readLine();
		}
		ret = sb.toString();
		if (br != null) {
			br.close();
		}
		if (read != null) {
			read.close();
		}
		return ret;
	}

	@Override
	public void saveEntity(Object entity) {
		sysMenuDao.saveEntity(entity);
	}

	@Override
	public List<Map<String, Object>> selectMenuIsvalId(long functionId) {
		return mapper.selectMenuIsvalId(functionId);
	}

	@Override
	public SysMenuEntity getEntityByFunctionCode(long functionCode) {
		return mapper.getEntityByFunctionCode(functionCode);
	}

	@Override
	public Map<String, SysMenu> getGroupMenuMapByFunctionCode() {
		Map<String, SysMenu> appGroupMap = new HashMap<>();

		List<SysMenu> allMenu = findAllMenu();
		for (SysMenu sysMenu : allMenu) {
			appGroupMap.put(sysMenu.getFunctionCode(), sysMenu);
		}
		return appGroupMap;
	}

	@Override
	public SysMenuEntity getEntityByPreDefineMenuId(long preDefineMenuId) {
		return mapper.getEntityByPreDefineMenuId(preDefineMenuId);
	}

	@Override
	public boolean queryMenuRepeat(String functionCode) {
		return this.menuMapper.menuCountByCode(functionCode) == 1;
	}

	@Override
	public List<SysMenu> findAllMenuAppAll() {
		return menuMapper.findAllMenuAppAll();
	}

	@Override
	public List<SysMenu> findAllMenuHisByRoleId(String roleId) {
		return menuMapper.findAllMenuHisByRoleId(roleId);
	}

}
