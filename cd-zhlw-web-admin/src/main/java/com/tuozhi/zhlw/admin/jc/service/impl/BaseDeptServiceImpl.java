package com.tuozhi.zhlw.admin.jc.service.impl;

import com.tuozhi.zhlw.admin.jc.entity.BaseDept;
import com.tuozhi.zhlw.admin.jc.entity.JCWorkFlowNode;
import com.tuozhi.zhlw.admin.jc.mapper.BaseDeptMapper;
import com.tuozhi.zhlw.admin.jc.mapper.FlowPathMapper;
import com.tuozhi.zhlw.admin.jc.mapper.WorkFlowNodeMapper;
import com.tuozhi.zhlw.admin.jc.service.BaseDeptService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseDeptServiceImpl implements BaseDeptService {
    @Autowired
    private BaseDeptMapper baseDeptMapper;
    @Autowired
    private WorkFlowNodeMapper workFlowNodeMapper;
    @Autowired
    private FlowPathMapper flowPathMapper;
    @Override
    public ResultMsg GetCurrentAndNextNodeData(Integer definationId,Integer CurrentNodeID, Long requestId,String deptId) {
        ResultMsg resultMsg=new ResultMsg();
        List<BaseDept> list=baseDeptMapper.findAllDept();
        BaseDept deptByDeptId1 = baseDeptMapper.findDeptByDeptId1(deptId);
        if(definationId==null) {
            definationId = (deptByDeptId1.getWorkFlowdeptRole() == 3) ? 101 : 100;
        }
        String LinkParentDeptID=GetStationLinkParentDeptID(list,deptByDeptId1.getId());
        List<JCWorkFlowNode> list1=workFlowNodeMapper.findWorkFlowNodeByDefinationId(definationId);
        if(CurrentNodeID==null){
         //如果没有当前节点编号，则认为是创建阶段
            for (JCWorkFlowNode jcWorkFlowNode:list1) {
                if("Start".equals(jcWorkFlowNode.getNodeType())){
                    if(!(jcWorkFlowNode.getWorkflowdeptrole().contains(deptByDeptId1.getWorkFlowdeptRole()+""))){
                        resultMsg = ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK);
                        resultMsg.setMessage("当前用户没有创建此业务的权限！");
                        return resultMsg;
                    }
                    CurrentNodeID=Integer.parseInt(jcWorkFlowNode.getNextNodeId());
                    break;
                }
            }
        }
        JCWorkFlowNode jcWorkFlowNode=new JCWorkFlowNode();
        JCWorkFlowNode jcWorkFlowNodex=new JCWorkFlowNode();
        //得到当前节点
        for (JCWorkFlowNode jcWorkFlowNode1:list1) {
            if(CurrentNodeID.intValue() == jcWorkFlowNode1.getNodeId().intValue()){
                jcWorkFlowNode = jcWorkFlowNode1;
                break;
            }
        }
        if(jcWorkFlowNode.getProcessLogic()==null){
            for (JCWorkFlowNode jcWorkFlowNode1:list1){
                if(jcWorkFlowNode.getNextNodeId().contains(jcWorkFlowNode1.getNodeId()+"")!= false){
                    jcWorkFlowNodex = jcWorkFlowNode1;
                    break;
                }
            }
        }else {
            //得到下一个节点
            switch (jcWorkFlowNode.getProcessLogic()) {
                case "AndSplit":
                    break;
                case "OrSplit":
                case "OrJoin,OrSplit":
                    //如果创建部门属于高速集团
                    Boolean isGaoShuJiTuan = false;
                    if (deptByDeptId1.getId() != null && LinkParentDeptID != null) {
                        isGaoShuJiTuan = (LinkParentDeptID.contains("5") != false);
                    }
                    for (JCWorkFlowNode jcWorkFlowNode1 : list1) {
                        if (jcWorkFlowNode.getNextNodeId().contains(jcWorkFlowNode1.getNodeId() + "") != false) {
                            if (isGaoShuJiTuan) {
                                if ("true".equals(jcWorkFlowNode1.getOrsplitValue())) {
                                    jcWorkFlowNodex = jcWorkFlowNode1;
                                    break;
                                }
                            } else {
                                if ("false".equals(jcWorkFlowNode1.getOrsplitValue())) {
                                    jcWorkFlowNodex = jcWorkFlowNode1;
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case "AndJoin":
                    break;
                case "OrJoin":
                default:
                    for (JCWorkFlowNode jcWorkFlowNode1 : list1) {
                        if (jcWorkFlowNode.getNextNodeId().contains(jcWorkFlowNode1.getNodeId() + "") != false) {
                            jcWorkFlowNodex = jcWorkFlowNode1;
                            break;
                        }
                    }
                    break;
            }
        }
        List BaseDeptlist=new ArrayList();
        if("End".equals(jcWorkFlowNodex.getNodeType())){
            //如果下一环节是结束环节
        } else if(jcWorkFlowNodex.getCorrespondNodeId()!=null){
            //DOTO 看不懂意思
            //如果下一环节有对应的环节（CORRESPOND_NODE_ID），则根据环节编号取部门编号
           Integer deptId1=flowPathMapper.findFlowPathByRequestId(requestId,jcWorkFlowNodex.getCorrespondNodeId());
           Integer NextDeptID =deptId1;
           BaseDeptlist=GetStationByID(list,NextDeptID);
        }else if(jcWorkFlowNodex.getWorkflowdeptrole()!=null){
             String arr1=jcWorkFlowNodex.getWorkflowdeptrole();
             String[] arr2=arr1.split(",");
            //如果有下个节点的角色id
            //根据WORKFLOWDEPTROLE取部门列表
            if(deptByDeptId1.getId()!=null){
                BaseDeptlist=findDeptByDeptId(deptByDeptId1.getDeptWork(),arr2);
                if(!(BaseDeptlist==null)){
                    //如果没有直达单位，则根据父节点链表取值
                    String[] split = LinkParentDeptID.split(",");
                    BaseDeptlist=baseDeptMapper.findDeptByParentId(split,arr2);
                }
            }else{
                BaseDeptlist=baseDeptMapper.findDeptByWorkflowdeptrole(arr2);
            }
        }else{
            //异常
        }
         Map map=new HashMap();
         map.put("CurrentNodeID",jcWorkFlowNode.getNodeId());
        map.put("NextNodeID",jcWorkFlowNodex.getNodeId());
        map.put("NextDeptArray",(BaseDeptlist ==null ? new ArrayList<>():BaseDeptlist));
        map.put("DEFINATION_ID",definationId);
        map.put("CurrentNode",jcWorkFlowNode);
        resultMsg.setData(map);
        return resultMsg;
    }

    @Override
    public List findDeptByDeptId(String deptId,String[] arr2) {
        return baseDeptMapper.findDeptByDeptId(deptId,arr2);
    }

    @Override
    public BaseDept findDeptByDeptId1(String deptId) {
        return baseDeptMapper.findDeptByDeptId1(deptId);
    }



    public String GetStationLinkParentDeptID(List<BaseDept> list, Integer parentId){
        String topParent = "";
        for (BaseDept baseDept:list) {
            if(baseDept.getId()==parentId){
                topParent=parentId+","+GetStationLinkParentDeptID(list,baseDept.getParentId());
                break;
            }
        }
        return topParent;
    }
    public List GetStationByID(List<BaseDept> list,Integer stationid){
        List list1=new ArrayList();
        for (BaseDept baseDept1:list) {
            if(null==baseDept1.getDeptWork()){
                continue;
            }
            if(Integer.parseInt(baseDept1.getDeptWork())==stationid){
               list1.add(baseDept1);
                break;
            }
        }
        return list1;
    }

}
