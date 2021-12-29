package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.entity.SysDeptEntity;
import com.tuozhi.zhlw.admin.mapper.DeptMapper;
import com.tuozhi.zhlw.admin.pojo.SysMenu;
import com.tuozhi.zhlw.admin.service.SysDeptService;


import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ma_zy
 * @Date: 2019/9/11 18:25
 * @Description:
 */
@Service
public class SysDeptServiceImpl extends AbstractMapperServiceImpl<DeptMapper, SysDeptEntity> implements SysDeptService {

    @Resource
    DeptMapper deptMapper;

    //    public CdError_List findAllBaseDept(List<DataPrivilegeEntity> curfilter, String minViewPrivilegeID ) {
//        return sysDeptDao.findAllBaseDept(curfilter,minViewPrivilegeID );
//    }
    public List<Map<String, Object>> findAllBaseDeptByParentId(String parentId, List<Map<String, Object>> list, boolean isContainsSelf) {
        return findAllBaseDeptByParentId(parentId, list, isContainsSelf, null);
    }

    public List<Map<String, Object>> findAllBaseDeptByParentId(String parentId, List<Map<String, Object>> list, boolean isContainsSelf, String allowSelectPrivilegeID) {
        List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            String deptid = String.valueOf(map.get("DEPTID"));
            String pId = String.valueOf(map.get("PARENTID"));
            Map<String, Object> objMap = new HashMap<String, Object>();
            if ((isContainsSelf == true && deptid.equals(parentId)) || (isContainsSelf == false && pId.equals(parentId))) {
                objMap.put("text", map.get("DEPTNAME"));
                objMap.put("deptId", map.get("DEPTID"));
                objMap.put("parentName", map.get("PARENTDEPTNAME"));
                objMap.put("pId", parentId);
                objMap.put("telephone", map.get("TELEPHONE"));
                objMap.put("deptWork", map.get("DEPTWORK"));
                objMap.put("iconCls", "x-fa fa-home");
                objMap.put("PRIVILEGEID", map.get("PRIVILEGEID"));
                objMap.put("PRIVILEGENAME", map.get("PRIVILEGENAME"));
                objMap.put("enableedit", map.get("ENABLEEDIT"));
                objMap.put("deptcode", map.get("DEPTCODE"));
                if (null != allowSelectPrivilegeID && ("," + allowSelectPrivilegeID + ",").contains("," + map.get("PRIVILEGEID").toString() + ",")) {
                    objMap.put("cls", "allowselectdept");
                }

                List<Map<String, Object>> childList = findAllBaseDeptByParentId(map.get("DEPTID").toString(), list, false, allowSelectPrivilegeID);
                if (childList.size() > 0) {
                    objMap.put("children", childList);
                    objMap.put("leaf", false);
                    objMap.put("expanded", true);
                } else {
                    objMap.put("leaf", true);
                }

                objList.add(objMap);

            }
        }
        return objList;
    }

    @Override
    public List<SysDeptEntity> findAllDeptList() {
        return deptMapper.findAllDept();
    }

    @Override
    public List createDeptTree(Long parentId, List<SysDeptEntity> deptList, boolean isContainsSelf, String allowSelectPrivilegeID) {
        List<Object> objList = new ArrayList<Object>();
        for (SysDeptEntity dept : deptList) {
                Map<String, Object> objMap = new HashMap<String, Object>();
                if ((isContainsSelf == true && dept.getId().equals(parentId)) ||
                        (isContainsSelf == false && dept.getParentId().equals(parentId))) {
                    objMap.put("text", dept.getDeptName());
                    objMap.put("deptId", dept.getId());
                    objMap.put("parentName", dept.getParentDeptName());
                    objMap.put("pId", parentId);
                    objMap.put("telephone", dept.getTelephone());
                    objMap.put("deptWork", dept.getDeptWork());
                    objMap.put("iconCls", "x-fa fa-home");
                    objMap.put("PRIVILEGEID", dept.getPrivilegeId());
                    objMap.put("workflowdeptrole", dept.getWorkflowdeptrole());
                    objMap.put("PRIVILEGENAME", dept.getPrivilegeName());
                    objMap.put("enableedit", dept.getEnableEdit());
                    objMap.put("deptcode", dept.getDeptCode());
                    objMap.put("deptLongId", dept.getDeptLongId());
                    

                    // if(null!= allowSelectPrivilegeID && (","+allowSelectPrivilegeID+",").contains(","+map.get("PRIVILEGEID").toString()+",")){
                    objMap.put("cls", "allowselectdept");
                    
                    //}

                    List<Map<String, Object>> childList = createDeptTree(dept.getId(), deptList, false, allowSelectPrivilegeID);
                    if (childList.size() > 0) {
                        objMap.put("children", childList);
                        objMap.put("leaf", false);
                        objMap.put("expanded", true);
                    } else {
                        objMap.put("leaf", true);
                    }
                    objList.add(objMap);
                }
        }
        return objList;
    }

	@Override
	public List DeptTreeCheckedByList(Long parentId, List<SysDeptEntity> deptList, boolean isContainsSelf,
			String allowSelectPrivilegeID,String[] arr) {
		List<Object> objList = new ArrayList<Object>();
        for (SysDeptEntity dept : deptList) {
                Map<String, Object> objMap = new HashMap<String, Object>();
                if ((isContainsSelf == true && dept.getId().equals(parentId)) ||
                        (isContainsSelf == false && dept.getParentId().equals(parentId))) {
                    objMap.put("text", dept.getDeptName());
                    objMap.put("deptId", dept.getId());
                    objMap.put("parentName", dept.getParentDeptName());
                    objMap.put("pId", parentId);
                    objMap.put("telephone", dept.getTelephone());
                    objMap.put("deptWork", dept.getDeptWork());
                    objMap.put("iconCls", "x-fa fa-home");
                    objMap.put("PRIVILEGEID", dept.getPrivilegeId());
                    objMap.put("workflowdeptrole", dept.getWorkflowdeptrole());
                    objMap.put("PRIVILEGENAME", dept.getPrivilegeName());
                    objMap.put("enableedit", dept.getEnableEdit());
                    objMap.put("deptcode", dept.getDeptCode());
                    
                    if(arr.length>0) {
                    	for (int i = 0; i < arr.length; i++) {
                    		if(arr[i].equals(dept.getDeptWork())) {
                    			
                            	System.err.println("进入"+dept.getDeptWork());
                            	objMap.put("checked", true);
                            }else {
                            	
                            	objMap.put("checked", false);
                            }
						}
                    }else {
                    	objMap.put("checked", false);
                    }
                    // if(null!= allowSelectPrivilegeID && (","+allowSelectPrivilegeID+",").contains(","+map.get("PRIVILEGEID").toString()+",")){
                    objMap.put("cls", "allowselectdept");
                    
                    //}

                    List<Map<String, Object>> childList = DeptTreeCheckedByList(dept.getId(), deptList, false, allowSelectPrivilegeID,arr);
                    if (childList.size() > 0) {
                        objMap.put("children", childList);
                        objMap.put("leaf", false);
                        objMap.put("expanded", true);
                    } else {
                        objMap.put("leaf", true);
                    }
                    objList.add(objMap);
                }
        }
        return objList;
	}
	@Override
	public List DeptTreeChecked(Long parentId, List<SysDeptEntity> deptList, boolean isContainsSelf,
			String allowSelectPrivilegeID) {
		List<Object> objList = new ArrayList<Object>();
        for (SysDeptEntity dept : deptList) {
                Map<String, Object> objMap = new HashMap<String, Object>();
                if ((isContainsSelf == true && dept.getId().equals(parentId)) ||
                        (isContainsSelf == false && dept.getParentId().equals(parentId))) {
                    objMap.put("text", dept.getDeptName());
                    objMap.put("deptId", dept.getId());
                    objMap.put("parentName", dept.getParentDeptName());
                    objMap.put("pId", parentId);
                    objMap.put("telephone", dept.getTelephone());
                    objMap.put("deptWork", dept.getDeptWork());
                    objMap.put("iconCls", "x-fa fa-home");
                    objMap.put("PRIVILEGEID", dept.getPrivilegeId());
                    objMap.put("workflowdeptrole", dept.getWorkflowdeptrole());
                    objMap.put("PRIVILEGENAME", dept.getPrivilegeName());
                    objMap.put("enableedit", dept.getEnableEdit());
                    objMap.put("deptcode", dept.getDeptCode());
                    objMap.put("checked", false);
//                    if("1010112".equals(dept.getDeptWork())) {
//                    	System.err.println("进入");
//                    	objMap.put("checked", true);
//                    }else {
//                    	
//                    	objMap.put("checked", false);
//                    }
                    // if(null!= allowSelectPrivilegeID && (","+allowSelectPrivilegeID+",").contains(","+map.get("PRIVILEGEID").toString()+",")){
                    objMap.put("cls", "allowselectdept");
                    
                    //}
                    List<Map<String, Object>> childList = DeptTreeChecked(dept.getId(), deptList, false, allowSelectPrivilegeID);
                    if (childList.size() > 0) {
                        objMap.put("children", childList);
                        objMap.put("leaf", false);
                        objMap.put("expanded", true);
                    } else {
                        objMap.put("leaf", true);
                    }
                    objList.add(objMap);
                }
        }
        return objList;
	}

    @Override
    public ResultMsg verifyExist(String deptWork) {
        ResultMsg result = new ResultMsg();
        SysDeptEntity sysDept = deptMapper.getDataByDeptWork(deptWork);
        if(sysDept != null){
            result.setSuccess(false);
            result.setMessage("该部门编号已存在");
            return result;
        }
        result.setSuccess(true);
        result.setMessage("该部门编号可以使用");
        return result;
    }

	@Override
	public List<SysDeptEntity> getAllChildIdsById(Long id) {
		return deptMapper.getAllChildIdsById(id);
	}


}
