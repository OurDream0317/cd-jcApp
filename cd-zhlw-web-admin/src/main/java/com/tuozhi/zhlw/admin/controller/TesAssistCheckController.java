package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.entity.TesAssistCheckEntity;
import com.tuozhi.zhlw.admin.entity.TesSuspectDateEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseIssuerEntity;
import com.tuozhi.zhlw.admin.service.TesAssistCheckService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assist")
@Slf4j
public class TesAssistCheckController extends BaseController{
	
	@Autowired
	TesAssistCheckService tescheckService;
	
	/**
	 * 1、查询所有信息2、根据车牌号搜索3、根据协查证据编号查询
	 * @param vehicleid
	 * @return
	 */
	@RequestMapping("/findAll")
    public ResultExtGrid findAll(@RequestParam(value = "vehicleid",required = false) String vehicleid,
    		@RequestParam(value = "sid",required = false) Long sid, @ModelAttribute QueryParams queryParams) {
		ResultExtGrid result = null;
		try {
			result = tescheckService.findAll(vehicleid, sid,queryParams);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
    }

	/**
	 * 新增
	 * @param assistentity
	 * @return
	 */
	@RequestMapping(value = "/save")
    public ResultMsg save(@ModelAttribute TesAssistCheckEntity assistentity,@RequestParam(value = "evidenceArr",required = false) String evidenceArr) {
		ResultMsg result = null;
		TesSuspectDateEntity tesSuspectDateEntity = new TesSuspectDateEntity();
		tesSuspectDateEntity.setEvidenceArr(evidenceArr);
		try {
			result = tescheckService.save(assistentity,tesSuspectDateEntity);
		}catch (Exception e){
			return systemErrorDispose(e,result);
		}
		return result;
		
    }

	/**
    *
    * 删除车辆信息
    */
   @RequestMapping(value = "/deletePlateInfo", method = RequestMethod.POST)
   @ResponseBody
   public ResultMsg delUserInfo(@RequestParam Long plateId) {
       ResultMsg resultMsg = null ;
           if (tescheckService.deleteUserByMark(plateId)){
               resultMsg = ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK) ;
           }else {
               resultMsg = ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR) ;
           }
       return resultMsg ;

   }

	/**
	 * 查询发行方服务机构编码
	 * @return
	 */
	@RequestMapping("findIssuerCode")
	public ResultExtGrid findBaseIssuer(){
		List<BaseIssuerEntity> xydata = tescheckService.findIssuerData();
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
