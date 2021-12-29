package com.tuozhi.zhlw.admin.controller;

import java.util.List;

import com.tuozhi.zhlw.admin.entity.TesEtcPassDataEntity;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tuozhi.zhlw.admin.entity.TesAssistCheckEntity;
import com.tuozhi.zhlw.admin.entity.TesEtcDataEntity;
import com.tuozhi.zhlw.admin.entity.TesEtcTotherDataEntity;
import com.tuozhi.zhlw.admin.service.TesEtcPassDataService;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;

@RestController
@RequestMapping("/etcpass")
@Slf4j
public class TesEtcPassDataController extends BaseController{
	
	@Autowired
	TesEtcPassDataService etcpassDataService;
	
	/**
	 * 根据协查车辆编号和证据来源查询对应的嫌疑数据
	 * @param sid
	 * @param evidence
	 * @return
	 */
	@RequestMapping("/findPassDataBySid")
    public ResultExtGrid findDataBySid(@RequestParam("sid") String sid,
								   @RequestParam("evidence") String evidence) {
		List<TesEtcPassDataEntity> xydata = etcpassDataService.findDataBySid(sid,evidence);
		return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,xydata,(long) xydata.size());
    }

	/**
	 * 通用的系统错误信息返回
	 * @param e
	 * @return
	 */
	private ResultMsg systemErrorDispose(Exception e, ResultMsg result) {
		log.error(e.getMessage());
		result = new ResultMsg();
		result.setSuccess(false);
		result.setMessage("系统错误");
		return result;
	}
}
