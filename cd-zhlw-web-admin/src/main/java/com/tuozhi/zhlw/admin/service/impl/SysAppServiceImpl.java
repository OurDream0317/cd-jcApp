package com.tuozhi.zhlw.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.dao.SysAppDao;
import com.tuozhi.zhlw.admin.entity.SysAppEntity;
import com.tuozhi.zhlw.admin.entity.SysCustomMenuEntity;
import com.tuozhi.zhlw.admin.mapper.AppMapper;
import com.tuozhi.zhlw.admin.service.SysAppService;
import com.tuozhi.zhlw.admin.service.SysCustomMenuService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author linqi
 * @create 2019/09/03 21:55
 **/
@Service
@Slf4j
public class SysAppServiceImpl extends BaseServiceImpl<SysAppEntity,Long>
        implements SysAppService {
    @Autowired
    SysAppDao sysAppDao;

    @Autowired
    //@Qualifier("entityManagerPrimary")
    EntityManager entityManager;

    @Resource
    AppMapper appMapper ;

    @Autowired
    SysCustomMenuService sysCustomMenuService;

    @Override
    public List<SysAppEntity> findAllCondition(Long userId) {
        List<Map<String, Object>> sysAppListMap = sysAppDao.findAllConditionByCondition(userId);
        List<SysAppEntity> sysAppEntityList = new ArrayList<>(10);
        for (Map<String, Object> map : sysAppListMap) {
            SysAppEntity sysAppEntity = new SysAppEntity();
            sysAppEntity.setId(((BigDecimal) map.get("ID")).longValue());
            sysAppEntity.setCode((String) map.get("CODE"));
            sysAppEntity.setCreateTime((Date) map.get("CREATETIME"));
            sysAppEntity.setName(((String) map.get("NAME")));
            sysAppEntity.setSort(((BigDecimal) map.get("SORT")).intValue());
//            sysAppEntity.setValidStatus(((BigDecimal) map.get("VALIDSTATUS")).intValue());
            sysAppEntity.setIconCls(((String) map.get("ICON_CLS")));
            sysAppEntityList.add(sysAppEntity);
        }
        return sysAppEntityList;
   }
    @Override
    public List<SysAppEntity> findAllCondition(String roleId) {
        List<Map<String, Object>> sysAppListMap = sysAppDao.findAllConditionByCondition(roleId);
        List<SysAppEntity> sysAppEntityList = new ArrayList<>(10);
        for (Map<String, Object> map : sysAppListMap) {
            SysAppEntity sysAppEntity = new SysAppEntity();
            sysAppEntity.setId(((BigDecimal) map.get("ID")).longValue());
            sysAppEntity.setCode((String) map.get("CODE"));
            sysAppEntity.setCreateTime((Date) map.get("CREATETIME"));
            sysAppEntity.setName(((String) map.get("NAME")));
            sysAppEntity.setSort(((BigDecimal) map.get("SORT")).intValue());
//            sysAppEntity.setValidStatus(((BigDecimal) map.get("VALIDSTATUS")).intValue());
            sysAppEntity.setIconCls(((String) map.get("ICON_CLS")));
            sysAppEntityList.add(sysAppEntity);
        }
        return sysAppEntityList;
   }

    @Override
    public PageInfo<SysAppEntity> findAllByPageHelper(QueryParams pageParams, String appName,Long appId) {

        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit());
        List<SysAppEntity> SysAppEntities = appMapper.findAllWithName(appName,appId) ;
        return new PageInfo<>(SysAppEntities);
    }

    @Override
    public List<SysAppEntity> findAll() {
        return appMapper.selectAll();
    }

    @Override
    public int saveSysAppEntity(SysAppEntity appEntity) {
        return appMapper.insertSelective(appEntity);
    }

    @Override
    public boolean deleteAppByMark(String appId) {
        return this.appMapper.deleteAppByMarkUpdate(appId) ;
    }

    @Override
    public boolean updateInfoById(SysAppEntity appEntity) {
        return this.appMapper.updateAppInfoById(appEntity);
    }

    private SysAppEntity findByAppCode(String appCode) {
        return appMapper.findByAppCode(appCode);
    }

    /**
     * 由于生成自定义菜单Tree方式和其他的不一样，需要追加到AppTree中
     * @return
     */
    @Override
    public void addCustomMenuTree(Map treeMap, Long userId) {
        SysAppEntity sysAppEntity = appMapper.findByAppCode("ZDYCD");
        List<SysCustomMenuEntity> customMenuList = sysCustomMenuService.findAllByUserId(userId);
        List childrenList = (List)treeMap.get("children");
        List customMenuTreeList = sysCustomMenuService.createCustomMenuTree(customMenuList);

        Map<String, Object> objMap = new HashMap<>();
        List customMenuAppList = new ArrayList();
        objMap.put("id", sysAppEntity.getId());
        objMap.put("parentId", 0);
        objMap.put("parentName", 0);
        objMap.put("text", sysAppEntity.getName());
        objMap.put("qtip", sysAppEntity.getName());
        objMap.put("orderindex", 1000);
        objMap.put("leaf", false);
        objMap.put("isLeaf", 0);
        objMap.put("iconCls",sysAppEntity.getIconCls());
        objMap.put("rowCls", "nav-tree-badge nav-tree-badge-new");
        objMap.put("children",customMenuTreeList);
        objMap.put("expanded", true);

        childrenList.add(objMap);
    }

    @Override
    public boolean checkRepeatAppName(String appName) {
        return this.appMapper.queryRepeatAppName(appName) == 1;
    }

    @Override
    public boolean checkRepeatAppCode(String appCode) {
        return this.appMapper.queryRepeatAppCode(appCode) == 1;
    }

    @Override
    public List<SysAppEntity> listByAvailable() {
        return appMapper.listByAvailable();
    }

	@Override
	public List<SysAppEntity> findAllCondition() {
		 List<Map<String, Object>> sysAppListMap = sysAppDao.findAllCondition();
	        List<SysAppEntity> sysAppEntityList = new ArrayList<>(10);
	        for (Map<String, Object> map : sysAppListMap) {
	            SysAppEntity sysAppEntity = new SysAppEntity();
	            sysAppEntity.setId(((BigDecimal) map.get("ID")).longValue());
	            sysAppEntity.setCode((String) map.get("CODE"));
	            sysAppEntity.setCreateTime((Date) map.get("CREATETIME"));
	            sysAppEntity.setName(((String) map.get("NAME")));
	            sysAppEntity.setSort(((BigDecimal) map.get("SORT")).intValue());
//	            sysAppEntity.setValidStatus(((BigDecimal) map.get("VALIDSTATUS")).intValue());
	            sysAppEntity.setIconCls(((String) map.get("ICON_CLS")));
	            sysAppEntityList.add(sysAppEntity);
	        }
	        return sysAppEntityList;
	}
	@Override
	public List<SysAppEntity> findAllConditionHis(String roleId) {
        List<Map<String, Object>> sysAppListMap = sysAppDao.findAllConditionHisByCondition(roleId);
        List<SysAppEntity> sysAppEntityList = new ArrayList<>(10);
        for (Map<String, Object> map : sysAppListMap) {
            SysAppEntity sysAppEntity = new SysAppEntity();
            sysAppEntity.setId(((BigDecimal) map.get("ID")).longValue());
            sysAppEntity.setCode((String) map.get("CODE"));
            sysAppEntity.setCreateTime((Date) map.get("CREATETIME"));
            sysAppEntity.setName(((String) map.get("NAME")));
            sysAppEntity.setSort(((BigDecimal) map.get("SORT")).intValue());
//            sysAppEntity.setValidStatus(((BigDecimal) map.get("VALIDSTATUS")).intValue());
            sysAppEntity.setIconCls(((String) map.get("ICON_CLS")));
            sysAppEntityList.add(sysAppEntity);
        }
        return sysAppEntityList;
   }

}
