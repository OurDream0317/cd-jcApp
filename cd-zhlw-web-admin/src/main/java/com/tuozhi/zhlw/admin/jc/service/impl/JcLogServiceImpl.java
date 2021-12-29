package com.tuozhi.zhlw.admin.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAddLog;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAttachment;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationlogFiles;
import com.tuozhi.zhlw.admin.jc.entity.JcOperationLogEntity;
import com.tuozhi.zhlw.admin.jc.mapper.JcLogMapper;
import com.tuozhi.zhlw.admin.jc.service.JcLogService;
import com.tuozhi.zhlw.admin.jc.util.JCOperationLogUtil;
import com.tuozhi.zhlw.admin.jc.util.UpFileUtil;
import com.tuozhi.zhlw.admin.jcApp.util;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static com.tuozhi.zhlw.admin.jcApp.util.getBase64String;

/**
 * @author wwx
 * @Date: 2019/12/2 12:06
 * @Description:
 */
@Service
public class JcLogServiceImpl implements JcLogService {

    @Autowired
    private JcLogMapper jcLogMapper;
    @Autowired
    private UpFileUtil upFileUtil;
    @Autowired
    private JCOperationLogUtil jcOperationLogUtil;

    @Override
    public void insertJcOperationLog(JcOperationLogEntity jcOperationLog) {
        this.jcLogMapper.insertJcLog(jcOperationLog);
    }

    @Override
    public PageInfo<JcOperationLogEntity> selectOperationLog(QueryParams queryParams, String vehiclelicense, Integer searchLicensecolorid, Date searchapplyBeginTime, Date searchapplyEndTime) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        Map map = new HashMap();
        map.put("vehiclelicense", vehiclelicense);
        map.put("searchLicensecolorid", searchLicensecolorid);
        map.put("searchapplyBeginTime", searchapplyBeginTime);
        map.put("searchapplyEndTime", searchapplyEndTime);
        List<JcOperationLogEntity> list = jcLogMapper.selectOperationLog(map);
        return new PageInfo<JcOperationLogEntity>(list);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOperationLog(JCOperationAddLog jcOperationAddLog, MultipartFile[] file, HttpServletRequest request) {
        String operationLogFileName = "imageOfJCOperationLog";
        if (jcOperationAddLog.getFlag().equals("add")) {
            List list = new ArrayList();
            this.jcLogMapper.addOperationLog(jcOperationAddLog);
            try {
                for (MultipartFile OneFile : file) {
                    if (OneFile.isEmpty()) {
                        continue;
                    }
                    JCOperationAttachment jcOperationAttachment = upFileUtil.upFileMethod(OneFile, operationLogFileName, request);
                    jcOperationAttachment.setJid(jcOperationAddLog.getId());
                    list.add(jcOperationAttachment);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            if (list.size() > 0) {
                this.jcLogMapper.addOperationAttchMent(list);
            }
            JcOperationLogEntity jcOperationAdd = new JcOperationLogEntity();
            jcOperationAdd.setVehiclelicense(jcOperationAddLog.getVehiclelicense());
            jcOperationAdd.setVehiclelicenseColor(jcOperationAddLog.getVehiclelicenseColor());
            jcOperationAdd.setVehicleType(jcOperationAddLog.getVehicleType());
            jcOperationAdd.setMisstollType(jcOperationAddLog.getEludeMoneyType());
            jcOperationAdd.setMisstollClass(jcOperationAddLog.getEludeMoneyTypeItem());
            jcOperationAdd.setTransferSource(6);
            jcOperationLogUtil.addJcOperationLog(jcOperationAdd, request);
        } else {
            this.jcLogMapper.updateOperationLog(jcOperationAddLog);
        }
    }

    @Override
    public PageInfo<JCOperationlogFiles> getOperationLogFiles(QueryParams queryParams, String vehicle, Integer vehicleColor) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<JCOperationlogFiles> list = jcLogMapper.getOperationLogFiles(vehicle, vehicleColor);
        return new PageInfo<JCOperationlogFiles>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOperationLogFiles(String vehicle, Integer vehicleColor, MultipartFile[] file, LoginUser loginUser) {
        List list = new ArrayList();
        try {
            for (MultipartFile OneFile : file) {
                JCOperationlogFiles jcOperationlogFiles = new JCOperationlogFiles();
                if (OneFile.isEmpty()) {
                    continue;
                }
                jcOperationlogFiles.setType(util.getFileType(OneFile));
                jcOperationlogFiles.setVehicle(vehicle);
                jcOperationlogFiles.setVehicleColor(vehicleColor);
                jcOperationlogFiles.setAttachmentPath(getBase64String(OneFile));
                jcOperationlogFiles.setUpFileDept(loginUser.getDeptName());
                jcOperationlogFiles.setUpFileUserName(loginUser.getUserName());
                list.add(jcOperationlogFiles);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException();
        }
        if (list.size() > 0) {
            this.jcLogMapper.addOperationLogFiles(list);
        }
    }

    @Override
    public void delOperationLogFiles(Long attachmentId) {
        jcLogMapper.delOperationLogFiles(attachmentId);
    }


    @Override
    public List<JCOperationlogFiles> getETCOperationLogFiles(String vehicle, Integer vehicleColor) {
        return jcLogMapper.getOperationLogFiles(vehicle, vehicleColor);
    }

    @Override
    public PageInfo<JCOperationAddLog> selectOperationAddLog(QueryParams queryParams, String vehiclelicense, Integer vehiclelicenseColor, Date createTime, LoginUser loginUser) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<JCOperationAddLog> list = jcLogMapper.selectOperationAddLog(vehiclelicense, vehiclelicenseColor, createTime, loginUser);
        return new PageInfo<JCOperationAddLog>(list);
    }

    @Override
    public List<JcOperationLogEntity> selectOperationLogByCarNum(JcOperationLogEntity jcOperationLog) {
        List<JcOperationLogEntity> list = jcLogMapper.selectOperationLogByCarNum(jcOperationLog);
        return list;
    }

    @Override
    public PageInfo<JCOperationAttachment> selectLogAttachmentByJId(QueryParams queryParams, Integer id) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<JCOperationAttachment> list = jcLogMapper.selectLogAttachmentByJId(id);
        return new PageInfo<JCOperationAttachment>(list);
    }

    @Override
    public String selectLogAttachmentById(Integer attachmentId) {
        return jcLogMapper.selectLogAttachmentById(attachmentId);
    }

    @Override
    public ResultExtGrid addLogAttachment(Long id, MultipartFile file, HttpServletRequest request) {
        String operationLogFileName = "imageOfJCOperationLog";
        JCOperationAttachment jcOperationAttachment = null;
        try {
            jcOperationAttachment = upFileUtil.upFileMethod(file, operationLogFileName, request);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultExtGridUtil.isSuccess(ResultMsgEnum.SAVE_ERROR);
        }
        jcOperationAttachment.setJid(id);
        List list = new ArrayList();
        list.add(jcOperationAttachment);
        jcLogMapper.addOperationAttchMent(list);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SAVE_OK);
    }

    @Override
    public ResultExtGrid deleteLogFile(Integer attachmentId) {
        try {
            jcLogMapper.deleteLogFile(attachmentId);
        } catch (Exception e) {
            return ResultExtGridUtil.isSuccess(ResultMsgEnum.DELETE_ERROR);
        }
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.DELETE_OK);
    }

    @Override
    public ResultExtGrid onDelOperationLog(Integer[] ids) {
        try {
            jcLogMapper.onDelOperationLog(ids);
        } catch (Exception e) {
            return ResultExtGridUtil.isSuccess(ResultMsgEnum.DELETE_ERROR);
        }
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.DELETE_OK);
    }
}
