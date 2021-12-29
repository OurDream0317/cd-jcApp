package com.tuozhi.zhlw.admin.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.RepairFeeDataInfoEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseAgencyEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseSectionEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseTollStationEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import org.springframework.stereotype.Service;

import com.tuozhi.zhlw.admin.entity.PayBackResultEntity;
import com.tuozhi.zhlw.admin.mapper.PayBackResultMapper;
import com.tuozhi.zhlw.admin.service.PayBackResultService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PayBackResultServiceImpl implements PayBackResultService {

	@Resource
	PayBackResultMapper paybackMapper;

	/**
	 * 新增车辆补费信息
	 * @param paybackentity
	 * @param platecolorId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResultMsg save(PayBackResultEntity paybackentity,String platecolorId,String repairFeeDatas,Integer design) {
		ResultMsg result = new ResultMsg();
		int countRow;
		paybackentity.setVehicleId(paybackentity.getVehicleId() + "_" + platecolorId);
		paybackentity.setPassId("000");
		BigDecimal oewFee = BigDecimal.valueOf(paybackentity.getOweFee()).multiply(new BigDecimal(100));
		paybackentity.setOweFee(oewFee.doubleValue());
		BigDecimal paybackFee = BigDecimal.valueOf(paybackentity.getPayBackFee()).multiply(new BigDecimal(100));
		paybackentity.setPayBackFee(paybackFee.doubleValue());
		if (design == 1){
			countRow = paybackMapper.save(paybackentity);
			if(countRow > 0){
				result.setSuccess(true);
				result.setMessage("保存成功！");
			}
		}else {
			countRow = paybackMapper.save(paybackentity);
			if (countRow > 0){
				JSONArray jsonArray = JSONArray.parseArray(repairFeeDatas);
				for(int i = 0;i < jsonArray.size(); i++){
					JSONObject Object = jsonArray.getJSONObject(i);
					RepairFeeDataInfoEntity repairFeeDataInfoEntity = new RepairFeeDataInfoEntity();
					repairFeeDataInfoEntity.setPassId(StringUtils.isEmpty(Object.get("passId")) ? "" : Object.get("passId").toString());
					repairFeeDataInfoEntity.setSid(paybackentity.getSid());
					repairFeeDataInfoEntity.setTransactionId(StringUtils.isEmpty(Object.get("transactionId")) ? "" : Object.get("transactionId").toString());
					repairFeeDataInfoEntity.setEnTime(StringUtils.isEmpty(Object.getDate("enTime")) ? null : Object.getDate("enTime"));
					repairFeeDataInfoEntity.setEnLaneId(StringUtils.isEmpty(Object.get("enLaneId")) ? "" : Object.get("enLaneId").toString());
					repairFeeDataInfoEntity.setExTime(StringUtils.isEmpty(Object.getDate("exTime")) ? null : Object.getDate("exTime"));
					repairFeeDataInfoEntity.setExLaneId(StringUtils.isEmpty(Object.get("exLaneId")) ? "" : Object.get("exLaneId").toString());
					repairFeeDataInfoEntity.setObuId(StringUtils.isEmpty(Object.get("obuId")) ? "" : Object.get("obuId").toString());
					repairFeeDataInfoEntity.setCardId(StringUtils.isEmpty(Object.get("cardId")) ? "" : Object.get("cardId").toString());
					repairFeeDataInfoEntity.setConfirmedEscapeType(StringUtils.isEmpty(Object.get("confirmedEscapeType")) ? "" : Object.get("confirmedEscapeType").toString());
					repairFeeDataInfoEntity.setMediaType(StringUtils.isEmpty(Object.get("mediaType")) ? "" : Object.get("mediaType").toString());
					repairFeeDataInfoEntity.setOweFee(StringUtils.isEmpty(Object.get("oweFee")) ? "" : Object.get("oweFee").toString());
					repairFeeDataInfoEntity.setPayBackStatus("1");
					repairFeeDataInfoEntity.setRealEnTime(StringUtils.isEmpty(Object.getDate("realEnTime")) ? null : Object.getDate("realEnTime"));
					repairFeeDataInfoEntity.setRealEnLaneId(StringUtils.isEmpty(Object.get("realEnLaneId")) ? "" : Object.get("realEnLaneId").toString());
					repairFeeDataInfoEntity.setRealExTime(StringUtils.isEmpty(Object.getDate("realExTime")) ? null : Object.getDate("realExTime"));
					repairFeeDataInfoEntity.setRealExLaneId(StringUtils.isEmpty(Object.get("realExLaneId")) ? "" : Object.get("realExLaneId").toString());
					repairFeeDataInfoEntity.setRealVehicleType(Long.valueOf(Object.get("realVehicleType").toString()));
					repairFeeDataInfoEntity.setRealVehicleClass(Long.valueOf(Object.get("realVehicleClass").toString()));
					int addCount = paybackMapper.saveRepairFeeInfo(repairFeeDataInfoEntity);
					if(addCount < 1){
						throw new RuntimeException("保存失败，请重试！");
					}
				}
				//更新车辆补费结果的通行标识ID
				int row = paybackMapper.uptPepairFeePassId(paybackentity.getSid());
				if(row < 1){
					throw new RuntimeException("修改失败，请重试！");
				}
			}else{
				result.setSuccess(false);
				result.setMessage("保存失败！");
			}
		}
		result.setSuccess(true);
		result.setMessage("保存成功！");
		return result;
	}

	/**
	 * 查询车辆补费结果信息
	 * @return
	 */
	@Override
	public ResultExtGrid  findResultAll(PayBackResultEntity payBackResultEntity, QueryParams queryParams){
		if(StringUtils.isEmpty(payBackResultEntity.getPlateId())){
			payBackResultEntity.setPlateId(null);
		}
		if(StringUtils.isEmpty(payBackResultEntity.getPlateColor())){
			payBackResultEntity.setPlateColor(null);
		}
		if(payBackResultEntity.getPlateId()!= null && payBackResultEntity.getPlateColor() != null){
			payBackResultEntity.setVehicleId(payBackResultEntity.getPlateId()+"_"+payBackResultEntity.getPlateColor());
		}
		//设置分页
		PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
		List<PayBackResultEntity> payBlackResultData = paybackMapper.findResultAll(payBackResultEntity);
		PageInfo pageInfo = new PageInfo<>(payBlackResultData);
		return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
	}

	/**
	 * 查询车辆补费数据信息
	 * @return
	 */
	@Override
	public List<RepairFeeDataInfoEntity> findRepairFeeData(Long sid){
		return paybackMapper.findRepairFeeData(sid);
	}


	@Override
	public List<Map<String,Object>> findRoadEnumOfFund(LoginUser loginUser) {
		return paybackMapper.findRoadEnumOfFund(loginUser.getDeptId());
	}
	@Override
	public List<Map<String,Object>> findRoadEnumOfGrey(LoginUser loginUser) {
		return paybackMapper.findRoadEnumOfGrey(loginUser.getDeptId());
	}
	/**
	 * 查询经办路段枚举值
	 */
	@Override
	public List<BaseSectionEntity> findChargeRoad(){
		return paybackMapper.findRoadEnum();
	}
	/**
	 * 查询经办站枚举值
	 */
	@Override
	public List<BaseTollStationEntity> findTollEnum(String sectionId){
		/*ResultMsg result = new ResultMsg();
		//将数据返回到前台
		Map<String, Object> map = new HashMap<String, Object>();
		BaseTollStationEntity baseTollStationEntity = paybackMapper.findTollEnum(sectionId);
		map.put("value",baseTollStationEntity.getId());
		map.put("name",baseTollStationEntity.getName());
		result.setSuccess(true);
		result.setData(map);*/
		return paybackMapper.findTollEnum(sectionId);
	}

	/**
	 * 查询经办单位信息，获取编码
	 * @return
	 */
	@Override
	public List<BaseAgencyEntity> findBaseAgency(){
		return paybackMapper.findBaseAgency();
	}

	@Override
	public List<PayBackResultEntity> createExcelLocalPay(Map<String, Object> conditions) {
		// TODO Auto-generated method stub
		List<PayBackResultEntity> list =paybackMapper.createExcelLocalPay(conditions);
		return list;
	}
}
