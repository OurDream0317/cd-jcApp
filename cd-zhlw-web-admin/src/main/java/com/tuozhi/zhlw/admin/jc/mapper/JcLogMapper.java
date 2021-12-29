package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.JCOperationAddLog;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAttachment;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationlogFiles;
import com.tuozhi.zhlw.admin.jc.entity.JcOperationLogEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wwx
 * @Date: 2019/12/2 12:08
 * @Description:
 */
@Repository
public interface JcLogMapper {

    void insertJcLog(JcOperationLogEntity jcOperationLogEntity);
    List<JcOperationLogEntity> selectOperationLog(Map map);
    void addOperationLog(JCOperationAddLog jcOperationAddLog);
    List<JCOperationAddLog> selectOperationAddLog(@Param("vehiclelicense") String vehiclelicense, @Param("vehiclelicenseColor") Integer vehiclelicenseColor, @Param("createTime") Date createTime, @Param("loginUser") LoginUser loginUser);
    void updateOperationLog(JCOperationAddLog jcOperationAddLog);
    List<JcOperationLogEntity> selectOperationLogByCarNum(JcOperationLogEntity jcOperationLog);
    void addOperationAttchMent(List list);
    void addOperationLogFiles(List list);
    List<JCOperationlogFiles> getOperationLogFiles(@Param("vehicle") String vehicle, @Param("vehicleColor") Integer vehicleColor);
    void delOperationLogFiles(@Param("attachmentId") Long attachmentId);
    List<JCOperationAttachment> selectLogAttachmentByJId(@Param("id")Integer id);
    String selectLogAttachmentById(@Param("attachmentId") Integer attachmentId);
    void deleteLogFile(@Param("attachmentId") Integer attachmentId);
    void onDelOperationLog(@Param("ids") Integer[] ids);
}
