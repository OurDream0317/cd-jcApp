package com.tuozhi.zhlw.admin.jc.util;

import com.tuozhi.zhlw.admin.controller.BaseController;
import com.tuozhi.zhlw.admin.jc.entity.JcOperationLogEntity;
import com.tuozhi.zhlw.admin.jc.service.JcLogService;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
@Slf4j
@Component
public class JCOperationLogUtil extends BaseController {
    @Autowired
    private JcLogService jcLogService ;
    /**
     * 新增稽查业务日志 JC_OPERATION_LOG
     * vehiclelicense 车牌号，必填
     * vehiclelicenseColor 车牌颜色，必填
     * vehicleType 车型（按照新表结构，TYPE为车型，CLASS为车种）
     * misstollType 逃费类型
     * misstollClass 逃费种类
     * transferSource 来源，必填 -- 1.协查 2.黑名单 3.稽查经费 4.灰名单 5.特情
     * @return 添加成功或失败返回对应的消息
     */
    public void addJcOperationLog(@Valid JcOperationLogEntity jcOperationLogEntity, HttpServletRequest request) {
        LoginUser loginUser = getLoginUser(request);
        jcOperationLogEntity.setCreateUserId(loginUser.getUserId());
        List<JcOperationLogEntity> list=jcLogService.selectOperationLogByCarNum(jcOperationLogEntity);
        if(list.size()<=0){
            try {
                this.jcLogService.insertJcOperationLog(jcOperationLogEntity);
            } catch (Exception e){
                log.error(ResultMsgEnum.ERROR.getMsg(),e);
            }
        }
    }

    /**
     * 稽查app新增黑名单（追缴）成功后添加日志
     * @param jcOperationLogEntity
     * @param loginUser
     */
    public void addJcOperationLogOfJCAPP(@Valid JcOperationLogEntity jcOperationLogEntity, LoginUser loginUser) {
        jcOperationLogEntity.setCreateUserId(loginUser.getUserId());
        List<JcOperationLogEntity> list=jcLogService.selectOperationLogByCarNum(jcOperationLogEntity);
        if(list.size()<=0){
            try {
                this.jcLogService.insertJcOperationLog(jcOperationLogEntity);
            } catch (Exception e){
                log.error(ResultMsgEnum.ERROR.getMsg(),e);
            }
        }
    }
}
