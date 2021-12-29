package com.tuozhi.zhlw.admin.jc.util;

import com.tuozhi.zhlw.admin.jc.mapper.BaseDeptMapper;
import com.tuozhi.zhlw.admin.mapper.RoleMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConstUtil implements ApplicationRunner {
    @Resource
    private BaseDeptMapper baseDeptMapper;


    /**
     * 黑名单添加
     */
    public static Integer JCBLACKLISTREQUEST_LOGICTYPE_ADD = 1;

    /**
     * 黑名单撤销添加
     */
    public static Integer JCBLACKLISTREQUEST_LOGICTYPE_REVOCATION = 2;

    /**
     * 稽查部部门ID
     */
    public static Long JCB_DEPT_ID;

    /**
     * 发行方部门ID
     */
    public static Long ISSUER_DEPT_ID;

    /**
     * 每个功能的流程数量
     */
    public static Map<Integer,List<Integer>> WORKFLOW_DEFINATION_ID;

    /**
     * 每个流程的结点
     */
    public static Map<Integer,List<Integer>> WORKFLOW_NODE_ID;

    static{
        WORKFLOW_DEFINATION_ID = new HashMap<>();
        WORKFLOW_NODE_ID = new HashMap<>();
        List<Integer> workflowDefinationIdList1 = new ArrayList<>();
        workflowDefinationIdList1.add(1);
        workflowDefinationIdList1.add(2);
        workflowDefinationIdList1.add(3);
        workflowDefinationIdList1.add(4);
        WORKFLOW_DEFINATION_ID.put(1,workflowDefinationIdList1);
        List<Integer> workflowDefinationIdList2 = new ArrayList<>();
        workflowDefinationIdList2.add(5);
        workflowDefinationIdList2.add(6);
        workflowDefinationIdList2.add(7);
        workflowDefinationIdList2.add(8);
        WORKFLOW_DEFINATION_ID.put(2,workflowDefinationIdList2);
        List<Integer> workflowDefinationIdList3 = new ArrayList<>();
        workflowDefinationIdList3.add(9);
        workflowDefinationIdList3.add(10);
        workflowDefinationIdList3.add(11);
        workflowDefinationIdList3.add(12);
        WORKFLOW_DEFINATION_ID.put(3,workflowDefinationIdList3);
        List<Integer> workflowDefinationIdList4 = new ArrayList<>();
        workflowDefinationIdList4.add(13);
        workflowDefinationIdList4.add(14);
        workflowDefinationIdList4.add(15);
        WORKFLOW_DEFINATION_ID.put(4,workflowDefinationIdList4);
        List<Integer> workflowNodeIdList1 = new ArrayList<>();
        workflowNodeIdList1.add(3);
        workflowNodeIdList1.add(5);
        workflowNodeIdList1.add(4);
        workflowNodeIdList1.add(1);
        WORKFLOW_NODE_ID.put(1,workflowNodeIdList1);
        List<Integer> workflowNodeIdList2 = new ArrayList<>();
        workflowNodeIdList2.add(5);
        workflowNodeIdList2.add(4);
        workflowNodeIdList2.add(1);
        WORKFLOW_NODE_ID.put(2,workflowNodeIdList2);
        List<Integer> workflowNodeIdList3 = new ArrayList<>();
        workflowNodeIdList3.add(3);
        workflowNodeIdList3.add(5);
        workflowNodeIdList3.add(1);
        WORKFLOW_NODE_ID.put(3,workflowNodeIdList3);
        List<Integer> workflowNodeIdList4 = new ArrayList<>();
        workflowNodeIdList4.add(5);
        workflowNodeIdList4.add(1);
        WORKFLOW_NODE_ID.put(4,workflowNodeIdList4);
        List<Integer> workflowNodeIdList5 = new ArrayList<>();
        workflowNodeIdList5.add(3);
        workflowNodeIdList5.add(5);
        workflowNodeIdList5.add(4);
        workflowNodeIdList5.add(1);
        WORKFLOW_NODE_ID.put(5,workflowNodeIdList5);
        List<Integer> workflowNodeIdList6 = new ArrayList<>();
        workflowNodeIdList6.add(5);
        workflowNodeIdList6.add(4);
        workflowNodeIdList6.add(1);
        WORKFLOW_NODE_ID.put(6,workflowNodeIdList6);
        List<Integer> workflowNodeIdList7 = new ArrayList<>();
        workflowNodeIdList7.add(3);
        workflowNodeIdList7.add(5);
        workflowNodeIdList7.add(1);
        WORKFLOW_NODE_ID.put(7,workflowNodeIdList7);
        List<Integer> workflowNodeIdList8 = new ArrayList<>();
        workflowNodeIdList8.add(5);
        workflowNodeIdList8.add(1);
        WORKFLOW_NODE_ID.put(8,workflowNodeIdList8);
        List<Integer> workflowNodeIdList9 = new ArrayList<>();
        workflowNodeIdList9.add(3);
        workflowNodeIdList9.add(5);
        workflowNodeIdList9.add(4);
        workflowNodeIdList9.add(1);
        WORKFLOW_NODE_ID.put(9,workflowNodeIdList9);
        List<Integer> workflowNodeIdList10 = new ArrayList<>();
        workflowNodeIdList10.add(5);
        workflowNodeIdList10.add(4);
        workflowNodeIdList10.add(1);
        WORKFLOW_NODE_ID.put(10,workflowNodeIdList10);
        List<Integer> workflowNodeIdList11 = new ArrayList<>();
        workflowNodeIdList11.add(3);
        workflowNodeIdList11.add(5);
        workflowNodeIdList11.add(1);
        WORKFLOW_NODE_ID.put(11,workflowNodeIdList11);
        List<Integer> workflowNodeIdList12 = new ArrayList<>();
        workflowNodeIdList12.add(5);
        workflowNodeIdList12.add(1);
        WORKFLOW_NODE_ID.put(12,workflowNodeIdList12);
        List<Integer> workflowNodeIdList13 = new ArrayList<>();
        workflowNodeIdList13.add(3);
        workflowNodeIdList13.add(5);
        workflowNodeIdList13.add(4);
        WORKFLOW_NODE_ID.put(13,workflowNodeIdList13);
        List<Integer> workflowNodeIdList14 = new ArrayList<>();
        workflowNodeIdList14.add(5);
        workflowNodeIdList14.add(4);
        WORKFLOW_NODE_ID.put(14,workflowNodeIdList14);
        List<Integer> workflowNodeIdList15 = new ArrayList<>();
        workflowNodeIdList15.add(3);
        workflowNodeIdList15.add(5);
        WORKFLOW_NODE_ID.put(15,workflowNodeIdList15);
    }


    /**
     * 启动项目时从数据库中获取数据
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*
        获取 稽查部部门ID和发行方部门ID
         */
        JCB_DEPT_ID = baseDeptMapper.getJCBDeptId();
        ISSUER_DEPT_ID = baseDeptMapper.getIssuerDeptId();
    }
}
