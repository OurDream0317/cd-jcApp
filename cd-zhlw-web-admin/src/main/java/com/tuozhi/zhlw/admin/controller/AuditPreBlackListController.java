package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.entity.AuditPreListDeviceEntity;
import com.tuozhi.zhlw.admin.entity.AuditPreblackListEntity;
import com.tuozhi.zhlw.admin.service.AuditPreBlackListService;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * 
 * @author liyuyan
 *
 */
@RestController
@RequestMapping("/audit")
public class AuditPreBlackListController extends BaseController{
	
	@Autowired
	AuditPreBlackListService auditlistService;
	
	/**
	 * 查询所有预追缴黑名单车辆
	 * @return
	 */
	@RequestMapping("/findAll")
    public ResultExtGrid findAll(AuditPreblackListEntity auditPreblackListEntity) {
        List<AuditPreblackListEntity> allData = auditlistService.findAll(auditPreblackListEntity);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,allData,(long) allData.size());
    }
	
	/**
	 * 根据关联查看涉及的OBU/ETC卡
	 * @param sid
	 * @return
	 */
	@RequestMapping("/findBySid")
    public ResultExtGrid findBySid(@RequestParam(value = "sid",required = false) Long sid) {
        List<AuditPreListDeviceEntity> xydata = auditlistService.findBySid(sid);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,xydata,(long) xydata.size());
    }
}
