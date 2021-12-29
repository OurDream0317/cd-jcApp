package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.entity.RepairFeeDataInfoEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseAgencyEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseSectionEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseTollStationEntity;
import com.tuozhi.zhlw.admin.jc.entity.Exc;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuozhi.zhlw.admin.entity.PayBackResultEntity;
import com.tuozhi.zhlw.admin.service.PayBackResultService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author liyuyan
 *
 */
@RestController
@RequestMapping("/payback")
@Slf4j
public class PayBackResultController extends BaseController{

	@Autowired
	PayBackResultService paybackService;
	
	@Autowired
    private RedisTemplate redisTemplate;

	/**
	 * 查询车辆补费信息
	 * @return
	 */
	@RequestMapping("/findpayBackResultAll")
	public ResultExtGrid findpayBackResultAll(@ModelAttribute PayBackResultEntity payBackResultEntity,@ModelAttribute QueryParams queryParams) {
		ResultExtGrid result = null;
		try {
			result = paybackService.findResultAll(payBackResultEntity,queryParams);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 导出
	 * */
	
	@RequestMapping("/createExcelLocalPay")
    public String export(HttpServletRequest request, HttpServletResponse repsonse) throws ParseException, JsonParseException, JsonMappingException, IOException {
        //查询用户数据

		Map<String, Object> conditions = new HashMap<String, Object>();
        
        
        
        String exstart = request.getParameter("startTime");
        String exend = request.getParameter("endTime");
		
		if (StringUtils.isNotEmpty(exstart)) {
            conditions.put("startTime", exstart);
        }
        if (StringUtils.isNotEmpty(exend)) {
            conditions.put("endTime", exend);
        }
        String vehiclelicense = request.getParameter("vehiclelicense");
        if (StringUtils.isNoneEmpty(vehiclelicense)) {
            conditions.put("vehiclelicense", vehiclelicense);
        }
        
        String missname = request.getParameter("missname");
        if (StringUtils.isNoneEmpty(missname)) {
            conditions.put("missname", missname);
        }
        String vehiclelicensecolor = request.getParameter("vehiclelicensecolor");
        if (StringUtils.isNoneEmpty(vehiclelicensecolor)) {
            conditions.put("vehiclelicensecolor", vehiclelicensecolor);
        }
        ResultMsg resultMsg = null;
		Set keys = redisTemplate.keys("ACCESS_TOKEN*");
		String key ="";
		Iterator<String> it1 = keys.iterator();
		while (it1.hasNext()) {
		  key=it1.next();
		}
		LoginUser loginUserForRedis = new ObjectMapper().readValue((String)redisTemplate.opsForValue().get(key),LoginUser.class);
		Long id=loginUserForRedis.getDeptId();
		String  roleId=loginUserForRedis.getRoleIds();
		int rid=Integer.parseInt(roleId);
		if(rid!=25) {
			if(id!=null) {
				conditions.put("id", id);
			}
		} 
        List<PayBackResultEntity> list = paybackService.createExcelLocalPay(conditions);

        //表头
        Map<String, String> headNameMap = new LinkedHashMap<String, String>();
        headNameMap.put("passId", "通行标识ID");
        headNameMap.put("plateId", "车牌号");
        headNameMap.put("plateColor", "车牌颜色");
        headNameMap.put("userCardId", "用户卡编号");
        headNameMap.put("paybackUser", "补费人姓名");
        headNameMap.put("paybackUserPhone", "补费人联系方式");
        headNameMap.put("transactionType", "补费类型");
        headNameMap.put("paybackType", "补费渠道");
        headNameMap.put("operator", "经办人姓名");
        headNameMap.put("operateOrg", "经办单位");
        headNameMap.put("operateRoad", "经办路段");
        headNameMap.put("operateStation", "经办站");
        headNameMap.put("oweFee", "欠费金额(元)");
        headNameMap.put("payBackFee", "补费金额(元)");
        headNameMap.put("payBackTime", "补费时间");

        //表格数据
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        if (list != null && list.size() > 0) {
            for (PayBackResultEntity user : list) {

                String PASSID = "";
                if (user.getPassId() != null) {
                	PASSID = user.getPassId();
                }
                String PLATEID = null;
                if (user.getPlateId() != null) {
                	PLATEID = user.getPlateId();
                }
                String PLATECOLOR = null;
                if (user.getPlateColor() != null) {
                	PLATECOLOR = user.getPlateColor();
                }
                String USERCARDID = "";
                if (user.getUserCardId() != null) {
                	USERCARDID = user.getUserCardId();
                }
                String PAYBACKUSER = null;
                if (user.getPaybackUser() != null) {
                	PAYBACKUSER = user.getPaybackUser();
                }
                String PAYBACKUSERPHONE = "";
                if (user.getPaybackUserPhone() != null) {
                	PAYBACKUSERPHONE = user.getPaybackUserPhone();
                }
                
                String TRANSACTIONTYPE = "";
                if (user.getTransactionType() != null) {
                	TRANSACTIONTYPE = user.getTransactionType();
                }
                String PAYBACKTYPE = "";
                if (user.getPaybackType() != null) {
                	PAYBACKTYPE = user.getPaybackType();
                }
                String OPERATOR = "";
                if (user.getOperator() != null) {
                	OPERATOR = user.getOperator();
                }
                String OPERATEORG = "";
                if (user.getOperateOrg() != null) {
                	OPERATEORG = user.getOperateOrg();
                }
                String OPERATEROAD = "";
                if (user.getOperateRoad() != null) {
                	OPERATEROAD = user.getOperateRoad();
                }
                String  OPERATESTATION= "";
                if (user.getOperateStation() != null) {
                	OPERATESTATION= user.getOperateStation();
                }
                Double OWEFEE = null;
                if (user.getOweFee() != null) {
                	OWEFEE = user.getOweFee();
                }
                Double PAYBACKFEE = null;
                if (user.getPayBackFee() != null) {
                	PAYBACKFEE = user.getPayBackFee();
                }
                Date PAYBACKTIME = null;
                if (user.getPayBackTime() != null) {
                	PAYBACKTIME = user.getPayBackTime();
                }
                

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("passId", PASSID);
                map.put("plateId", PLATEID);
                map.put("plateColor", PLATECOLOR);
                map.put("userCardId", USERCARDID);
                map.put("paybackUser", PAYBACKUSER);
                map.put("paybackUserPhone", PAYBACKUSERPHONE);
                map.put("transactionType", TRANSACTIONTYPE);
                map.put("paybackType", PAYBACKTYPE);
                map.put("operator", OPERATOR);
                map.put("operateOrg", OPERATEORG);
                map.put("operateRoad", OPERATEROAD);
                map.put("operateStation", OPERATESTATION);
                map.put("oweFee", OWEFEE);
                map.put("payBackFee", PAYBACKFEE);
                map.put("payBackTime", PAYBACKTIME);
                list1.add(map);
            }
        }
    
	
		LoginUser loginUserForRedis1 = new ObjectMapper().readValue((String)redisTemplate.opsForValue().get(key),LoginUser.class);
      //  LoginUser loginUser =redisTemplate.getLoginUser(request);
        String userName = loginUserForRedis1.getUserName();
        Exc.exportXlsx(repsonse, userName, headNameMap, list1);
        return null;
    }

	/**
	 * 新增
	 * @param paybackentity
	 * @return
	 */
	@RequestMapping(value = "/save")
    public ResultMsg save(PayBackResultEntity paybackentity,
						  @RequestParam(value = "platecolorId",required = false) String platecolorId,
						  @RequestParam(value = "repairFeeDatas",required = false) String repairFeeDatas,
						  @RequestParam(value = "payBackDescription",required = false) String payBackDescription,
						  @RequestParam(value = "design",required = false) Integer design) {
		paybackentity.setPayBackDescription(payBackDescription);
		ResultMsg resultMsg = paybackService.save(paybackentity, platecolorId,repairFeeDatas,design);
		return ResultMsgUtil.isSuccess(ResultMsgEnum.UPDATE_OK);
    }

	/**
	 * 查询车辆补费数据信息
	 * @return
	 */
	@RequestMapping("/findRepairBySid")
    public ResultExtGrid findRepairFeeAll(@RequestParam(value = "sid",required = false) Long sid){
		List<RepairFeeDataInfoEntity> xydata = paybackService.findRepairFeeData(sid);
		return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,xydata,(long) xydata.size());
	}

	/**
	 * 查询经办路段
	 * @return
	 */
	@RequestMapping("/findRoadEnum")
	public ResultExtGrid findChargeRoad(){
        List<BaseSectionEntity> xydata = paybackService.findChargeRoad();
		return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,xydata,(long) xydata.size());
	}
    /**
     * 稽查经费查询经办路段
     * @return
     */
    @RequestMapping("/findRoadEnumOfFund")
    public ResultExtGrid findChargeRoad(HttpServletRequest request){
        LoginUser loginUser = getLoginUser(request);
        if(Objects.isNull(loginUser)){
            log.error(ResultMsgEnum.RETURN_LOGIN.getMsg());
            return ResultExtGridUtil.isError(ResultMsgEnum.RETURN_LOGIN);
        }
        List<Map<String,Object>> xydata = paybackService.findRoadEnumOfFund(loginUser);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,xydata,(long) xydata.size());
    }

    /**
     * 关注名单查询经办路段
     * @return
     */
    @RequestMapping("/findRoadEnumOfGrey")
    public ResultExtGrid findRoadEnumOfGrey(HttpServletRequest request){
        LoginUser loginUser = getLoginUser(request);
        if(Objects.isNull(loginUser)){
            log.error(ResultMsgEnum.RETURN_LOGIN.getMsg());
            return ResultExtGridUtil.isError(ResultMsgEnum.RETURN_LOGIN);
        }
        List<Map<String,Object>> xydata = paybackService.findRoadEnumOfGrey(loginUser);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,xydata,(long) xydata.size());
    }
	/**
	 * 查询经办站
	 * @return
	 */
	@RequestMapping("/findTollEnum")
	public ResultExtGrid findChargeToll(@RequestParam(value = "sectionId",required = false) String sectionId){
		List<BaseTollStationEntity> xydata = paybackService.findTollEnum(sectionId);
		return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,xydata,(long) xydata.size());
	}

	/**
	 * 查询经办单位编码
	 */
	@RequestMapping("/findAgencyEnum")
	public ResultExtGrid findBaseAgency(){
		List<BaseAgencyEntity> xydata = paybackService.findBaseAgency();
		return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,xydata,(long) xydata.size());
	}
}
