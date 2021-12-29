package com.tuozhi.zhlw.admin.jc.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAddLog;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAttachment;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationlogFiles;
import com.tuozhi.zhlw.admin.jc.entity.JcOperationLogEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author wwx
 * @Date: 2019/12/2 10:25
 * @Description:
 */
public interface JcLogService {

    void insertJcOperationLog(JcOperationLogEntity jcOperationLog);
    void addOperationLogFiles(String vehicle, Integer vehicleColor, MultipartFile[] file, LoginUser loginUser) throws IOException;
    void delOperationLogFiles(Long attachmentId);
    List<JCOperationlogFiles> getETCOperationLogFiles(String vehicle, Integer vehicleColor);
    List<JcOperationLogEntity> selectOperationLogByCarNum(JcOperationLogEntity jcOperationLog);
    PageInfo<JcOperationLogEntity> selectOperationLog(QueryParams queryParams, String vehiclelicense,Integer searchLicensecolorid,Date searchapplyBeginTime,Date searchapplyEndTime);
    void addOperationLog(JCOperationAddLog jcOperationAddLog, MultipartFile[] file, HttpServletRequest request);
    PageInfo<JCOperationlogFiles> getOperationLogFiles(QueryParams queryParams,String vehicle, Integer vehicleColor);
    PageInfo<JCOperationAddLog> selectOperationAddLog(QueryParams queryParams, String vehiclelicense, Integer vehiclelicenseColor, Date createTime, LoginUser loginUser);
    PageInfo<JCOperationAttachment> selectLogAttachmentByJId(QueryParams queryParams, Integer id);
    String selectLogAttachmentById(Integer attachmentId);
    ResultExtGrid addLogAttachment(Long id, MultipartFile file, HttpServletRequest request);
    ResultExtGrid deleteLogFile(Integer attachmentId);
    ResultExtGrid onDelOperationLog(Integer[] ids);
}
