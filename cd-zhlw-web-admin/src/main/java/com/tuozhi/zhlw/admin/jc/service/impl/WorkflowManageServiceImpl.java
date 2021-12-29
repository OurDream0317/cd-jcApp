package com.tuozhi.zhlw.admin.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.JcWorkFlowNodeEntity;
import com.tuozhi.zhlw.admin.jc.entity.JcWorkflowDefinationEntity;
import com.tuozhi.zhlw.admin.jc.mapper.BaseDeptMapper;
import com.tuozhi.zhlw.admin.jc.mapper.JcWorkFlowNodeMapper;
import com.tuozhi.zhlw.admin.jc.mapper.JcWorkflowDefinationMapper;
import com.tuozhi.zhlw.admin.jc.service.WorkflowManageService;
import com.tuozhi.zhlw.admin.jc.util.ConstUtil;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import com.tuozhi.zhlw.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName WorkflowManageServiceImpl
 * @Descriotion TODO 流程步骤配置Service实现
 * @Author fujiankang
 * @Date 2019/11/12 16:11
 * @Version 1.0
 */
@Service
public class WorkflowManageServiceImpl implements WorkflowManageService {
    @Resource
    private JcWorkflowDefinationMapper jcWorkflowDefinationMapper;
    @Resource
    private JcWorkFlowNodeMapper jcWorkFlowNodeMapper;
    @Resource
    private BaseDeptMapper baseDeptMapper;

    @Override
    public ResultExtGrid getWorkflowDefinationListByParam(JcWorkflowDefinationEntity jcWorkflowDefinationEntity, QueryParams queryParams) {
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());

        //查询
        List<JcWorkflowDefinationEntity> jcWorkflowDefinationList = jcWorkflowDefinationMapper.getDataByParam(jcWorkflowDefinationEntity);
        PageInfo pageInfo = new PageInfo<>(jcWorkflowDefinationList);
        //返回数据
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg insertWorkflow(JcWorkflowDefinationEntity jcWorkflowDefinationEntity) {
        ResultMsg result = new ResultMsg();
        Date newDate = new Date();
        boolean highSpeedGroup = false;
        if (jcWorkflowDefinationEntity.getWorkFlowDeptRole().contains("4")) {
            highSpeedGroup = true;
        }
        if (jcWorkflowDefinationEntity.getDefinationMenu() != 4 && !jcWorkflowDefinationEntity.getWorkFlowDeptRole().contains("1")) {
            result.setSuccess(false);
            result.setMessage("结束环节必须是稽查部");
            return result;
        } else if (jcWorkflowDefinationEntity.getDefinationMenu() == 4 && jcWorkflowDefinationEntity.getWorkFlowDeptRole().contains("1")) {
            result.setSuccess(false);
            result.setMessage("灰名单不可选择稽查部作为流程结点");
            return result;
        }
        if (StringUtils.isEmpty(jcWorkflowDefinationEntity.getWorkFlowDeptRole())) {
            result.setSuccess(false);
            result.setMessage("请选择环节角色");
            return result;
        }
        String[] workDeptRoles = jcWorkflowDefinationEntity.getWorkFlowDeptRole().split(",");
        if (workDeptRoles.length < 2) {
            result.setSuccess(false);
            result.setMessage("环节角色不可少于2个");
            return result;
        }
        //获取当前流程的所有初始部门
        String definationInitDept = baseDeptMapper.getDataByWorkFlowDeptRole(Integer.parseInt(workDeptRoles[0]), highSpeedGroup);
        //查询是否有相同的初始部门数据，不能创建多个相同初始部门的流程
        int count = jcWorkflowDefinationMapper.getCountByDefinationInitDept(definationInitDept, jcWorkflowDefinationEntity.getDefinationMenu());
        if (count > 0) {
            result.setSuccess(false);
            result.setMessage("流程已存在,请重新选择环节角色");
            return result;
        }
        jcWorkflowDefinationEntity.setCreateTime(newDate);
        jcWorkflowDefinationEntity.setDefinationInitDept(definationInitDept);
        //根据程序配置的常量设置 流程ID
        jcWorkflowDefinationEntity.setDefinationId(null);
        List<Integer> workflowDefinationIdList = ConstUtil.WORKFLOW_DEFINATION_ID.get(jcWorkflowDefinationEntity.getDefinationMenu());
        if(workflowDefinationIdList.isEmpty()){
            result.setSuccess(false);
            result.setMessage("没有对应的流程所属");
            return result;
        }
        for (Integer workflowDefinationId : workflowDefinationIdList) {
            List<Integer> workflowNodeIdArr = ConstUtil.WORKFLOW_NODE_ID.get(workflowDefinationId);
            boolean workflowDefinationIdBoolean = true;
            for (int i = 0; i < workDeptRoles.length; i++) {
                if(!workflowNodeIdArr.contains(Integer.parseInt(workDeptRoles[i]))){
                    workflowDefinationIdBoolean = false;
                    break;
                }
            }
            if(workDeptRoles.length == workflowNodeIdArr.size() && workflowDefinationIdBoolean){
                jcWorkflowDefinationEntity.setDefinationId(workflowDefinationId);
                break;
            }
        }
        //判断是否有对应的流程常量
        if(jcWorkflowDefinationEntity.getDefinationId() == null){
            result.setSuccess(false);
            result.setMessage("系统流程常量配置错误");
            return result;
        }
        int addCount = jcWorkflowDefinationMapper.insertData(jcWorkflowDefinationEntity);
        if (addCount < 1) {
            result.setSuccess(false);
            result.setMessage("保存失败");
        }
        //生成流程节点数据
        Integer prevNodeId = null;
        JcWorkFlowNodeEntity jcWorkFlowNode = null;
        for (int i = 0; i < workDeptRoles.length; i++) {
            jcWorkFlowNode = new JcWorkFlowNodeEntity();
            jcWorkFlowNode.setNodeId(Integer.parseInt(workDeptRoles[i]));
            jcWorkFlowNode.setDefinationId(jcWorkflowDefinationEntity.getDefinationId());
            jcWorkFlowNode.setWorkFlowDeptRole(Integer.parseInt(workDeptRoles[i]));
            jcWorkFlowNode.setCreateTime(newDate);
            if (i != 0) {
                jcWorkFlowNode.setPrevNodeId(prevNodeId);
            } else {
                jcWorkFlowNode.setNodeType(1);
            }
            if (i == (workDeptRoles.length - 1)) {
                jcWorkFlowNode.setNodeType(3);
            }
            if (i != 0 && i != (workDeptRoles.length - 1)) {
                jcWorkFlowNode.setNodeType(2);
            }
            addCount = jcWorkFlowNodeMapper.insertData(jcWorkFlowNode);
            if (addCount < 1) {
                throw new RuntimeException("保存失败!");
            }
            if (i != 0) {
                JcWorkFlowNodeEntity jcWorkFlowNodeEntity = new JcWorkFlowNodeEntity();
                jcWorkFlowNodeEntity.setNodeId(prevNodeId);
                jcWorkFlowNodeEntity.setDefinationId(jcWorkflowDefinationEntity.getDefinationId());
                jcWorkFlowNodeEntity.setNextNodeId(jcWorkFlowNode.getNodeId());
                int updateCount = jcWorkFlowNodeMapper.updateData(jcWorkFlowNodeEntity);
                if (updateCount < 1) {
                    throw new RuntimeException("保存失败!");
                }
            }
            prevNodeId = jcWorkFlowNode.getNodeId();
        }
        result.setSuccess(true);
        result.setMessage("保存成功");
        return result;
    }

    @Override
    public ResultMsg getWorkflowNodeByDefinationId(Integer definationId) {
        ResultMsg result = new ResultMsg();
        List<JcWorkFlowNodeEntity> jcWorkFlowNodeList = jcWorkFlowNodeMapper.getDataByDefinationId(definationId);
        result.setSuccess(true);
        result.setData(jcWorkFlowNodeList);
        result.setMessage("查询成功");
        return result;
    }

    @Override
    public ResultMsg updateWorkFlowNodeByNodeId(JcWorkFlowNodeEntity jcWorkFlowNode) {
        ResultMsg result = new ResultMsg();
        int updateCount = jcWorkFlowNodeMapper.updateData(jcWorkFlowNode);
        if (updateCount < 1) {
            result.setSuccess(false);
            result.setMessage("保存失败");
            return result;
        }
        result.setSuccess(true);
        result.setMessage("保存成功");
        return result;
    }

    @Override
    public ResultMsg updateWorkFlowDefinationByDefinationId(JcWorkflowDefinationEntity jcWorkflowDefinationEntity) {
        ResultMsg result = new ResultMsg();
        int updateCount = jcWorkflowDefinationMapper.updateDataByDefinationId(jcWorkflowDefinationEntity);
        if (updateCount < 1) {
            result.setSuccess(false);
            if (jcWorkflowDefinationEntity.getDefinationStatus() == 4) {
                result.setMessage("运行失败");
            } else if (jcWorkflowDefinationEntity.getDefinationStatus() == 2) {
                result.setMessage("停止失败");
            }
            return result;
        }
        result.setSuccess(true);
        if (jcWorkflowDefinationEntity.getDefinationStatus() == 4) {
            result.setMessage("运行成功");
        } else if (jcWorkflowDefinationEntity.getDefinationStatus() == 2) {
            result.setMessage("停止成功");
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg deleteWorkFlowDefination(List<Integer> definationIdList) {
        ResultMsg result = new ResultMsg();
        //删除 该流程数据
        int deleteCount = jcWorkflowDefinationMapper.deleteDataByDefinationId(definationIdList);
        if (deleteCount < 1) {
            result.setSuccess(false);
            result.setMessage("删除失败");
            return result;
        }
        //删除 该流程的结点数据
        deleteCount = jcWorkFlowNodeMapper.deleteDataByDefinationId(definationIdList);
        if (deleteCount < 1) {
            throw new RuntimeException("删除失败");
        }
        result.setSuccess(true);
        result.setMessage("删除成功");
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg updateWorkFlowDefinationInitDept(List<Integer> definationIdList) {
        ResultMsg result = new ResultMsg();
        //批量更新流程的初始部门ID
        for (Integer definationId : definationIdList) {
            //获取 当前流程 数据
            JcWorkflowDefinationEntity jcWorkflowDefination = jcWorkflowDefinationMapper.getDataByDefinationId(definationId);
            //获取当前流程的所有结点
            List<JcWorkFlowNodeEntity> jcWorkFlowNodeList = jcWorkFlowNodeMapper.getDataByDefinationId(definationId);
            //获取初始环节的有权处理角色和判断当前流程是否有事业部结点
            boolean ifSyb = false;
            Integer workFlowDeptRole = null;
            for (JcWorkFlowNodeEntity jcWorkFlowNode : jcWorkFlowNodeList) {
                if (jcWorkFlowNode.getNodeType() == 1) {
                    workFlowDeptRole = jcWorkFlowNode.getWorkFlowDeptRole();
                }
                if (jcWorkFlowNode.getWorkFlowDeptRole() == 4) {
                    ifSyb = true;
                    //break;
                }
            }
            //获取当前流程的所有初始部门
            String definationInitDept = baseDeptMapper.getDataByWorkFlowDeptRole(workFlowDeptRole, ifSyb);
            //查询是否有相同的初始部门数据，不能创建多个相同初始部门的流程
            int count = jcWorkflowDefinationMapper.getCountByDefinationInitDept(definationInitDept, jcWorkflowDefination.getDefinationMenu());
            if (count > 0) {
                throw new RuntimeException("流程已存在,流程编号:" + definationId);
            }
            jcWorkflowDefination = new JcWorkflowDefinationEntity();
            jcWorkflowDefination.setDefinationId(definationId);
            jcWorkflowDefination.setDefinationInitDept(definationInitDept);
            int updateCount = jcWorkflowDefinationMapper.updateDataByDefinationId(jcWorkflowDefination);
            if (updateCount < 1) {
                throw new RuntimeException("修改失败,流程编号:" + definationId);
            }
        }
        result.setSuccess(true);
        result.setMessage("修改成功");
        return result;
    }
}