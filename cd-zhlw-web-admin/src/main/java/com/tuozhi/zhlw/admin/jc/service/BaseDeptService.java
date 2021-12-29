package com.tuozhi.zhlw.admin.jc.service;

import com.tuozhi.zhlw.admin.jc.entity.BaseDept;
import com.tuozhi.zhlw.common.bean.ResultMsg;

import java.util.List;

public interface BaseDeptService {

    ResultMsg GetCurrentAndNextNodeData(Integer definationId, Integer CurrentNodeID, Long requestId, String deptId);

    List findDeptByDeptId(String deptId, String[] arr2);

    BaseDept findDeptByDeptId1(String deptId);
}
