package com.tuozhi.zhlw.admin.jc.service.impl;

import com.tuozhi.zhlw.admin.jc.entity.JCNewDataOfPassId;
import com.tuozhi.zhlw.admin.jc.entity.JCNewTradePassDate;
import com.tuozhi.zhlw.admin.jc.entity.TradePassEntity;
import com.tuozhi.zhlw.admin.jc.mapper.ExitPassDataMapper;
import com.tuozhi.zhlw.admin.jc.mapper.TechFeerateIntervalMapper;
import com.tuozhi.zhlw.admin.jc.mapper.TransactionPasstuDetailMapper;
import com.tuozhi.zhlw.admin.jc.service.ExitPassDataService;
import com.tuozhi.zhlw.admin.jc.util.ExcelUtils;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExitPassDataServiceImpl implements ExitPassDataService {
    @Autowired
    private ExitPassDataMapper exitPassDataMapper;

    @Autowired
    private TransactionPasstuDetailMapper transactionPasstuDetailMapper;

    @Autowired
    private TechFeerateIntervalMapper techFeerateIntervalMapper;
    @Override
    public List<TradePassEntity> getTradePassByParams(Map<String, Object> conditions) {
        List<TradePassEntity> list = exitPassDataMapper.getTradePassDataByParams(conditions);
        return list;
    }

    @Override
    public void selectNewTradePassListExport(Map<String, Object> conditions, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = exitPassDataMapper.selectNewTradePassListExport(conditions);
        if("1".equals(conditions.get("flag"))) {
            String[] titles = {"入口时间", "出口时间", "入口车牌", "出口车牌", "入口车型", "出口车型","入口轴数","出口轴数","计费车型", "判定车型", "入口车种",
                    "出口车种", "入口路段", "出口路段", "入口收费站", "出口收费站", "交易金额", "应收金额", "最小费额交易金额", "判定金额（门架）", "欠费金额", "本省最小通行费", "全省最小通行费", "发行省份", "入口省份", "出口省份", "车辆长", "车辆宽", "车辆高", "出口重量",
                    "通行介质", "CPC卡号或ETC卡号", "OBU编号", "入口收费站编号", "出口收费站编号", "入口车道编码", "出口车道编码", "特情类型", "交易支付方式", "通行标识","出口流水号","出口计费方式","行程类型"};
            String[] rows = {"ENTIME", "EXTIME", "ENVEHICLEID", "EXVEHICLEID", "ENVEHICLETYPE", "EXVEHICLETYPE","ENAXLECOUNT","EXAXLECOUNT","FEEVEHICLETYPE", "NEWVEHICLETYPE", "ENVEHICLECLASS",
                    "EXVEHICLECLASS", "ENSECTIONID", "EXSECTIONID", "ENTOLLSTATIONNAME", "EXTOLLSTATIONNAME", "FEE", "PAYFEE", "SHORTFEE", "REALFEE", "OWEFEE", "OWERFEE", "ALLFEE", "ISSUERNAME", "ENPROVINCENAME", "EXPROVINCENAME", "VEHICLELENGTH", "VEHICLEWIDTH", "VEHICLEHIGHT", "EXWEIGHT",
                    "MEDIATYPE", "MEDIANO", "OBUID", "ENTOLLSTATIONID", "EXTOLLSTATIONID", "ENTOLLLANEID", "EXTOLLLANEID", "SPECIALTYPE", "PAYTYPE", "PASSID","EXID","EXITFEETYPE","CD_PATHFITTINGCLASS"};
            ExcelUtils.excelUtil(response, titles, list, rows, "通行数据");
        }else if("0".equals(conditions.get("flag"))) {
            String[] titles = {"出口车牌", "入口时间", "出口时间", "入口收费站", "出口收费站","入口收费站编号", "出口收费站编号","CPC卡号或ETC卡号", "OBU编号", "出口车型", "计费车型","判定车型","应收金额","判定金额（门架）","欠费金额","本省最小费额通行费","全省最小费额通行费","PASSID","出口交易编号","是否为多省交易（0单省1多省）"};
            String[] rows = {"EXVEHICLEID","ENTIME", "EXTIME","ENTOLLSTATIONNAME", "EXTOLLSTATIONNAME", "ENTOLLSTATIONID", "EXTOLLSTATIONID", "MEDIANO", "OBUID","EXVEHICLETYPE","FEEVEHICLETYPE", "NEWVEHICLETYPE","PAYFEE", "REALFEE","OWEFEE","OWERFEE","ALLFEE","PASSID","EXID","MULTIPROVINCE"};
            ExcelUtils.excelUtil(response, titles, list, rows, "出口通行补费数据");
        }
    }

    @Override
    public long selectAllDataCount(Map<String, Object> conditions) {
        // TODO Auto-generated method stub
        long count = exitPassDataMapper.selectAllDataCount(conditions);
        return count;
    }

    @Override
    public List<Map<String, Object>> splitAmountByProvince(String passId,String listId,Integer mediatype) {
        List<Map<String, Object>> list=null;
        if(mediatype==1) {
            list = exitPassDataMapper.splitAmountByProvinceOfETC(passId);
        }else {
            list = exitPassDataMapper.splitAmountByProvinceOfCPC(listId);
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg newCarCurrentVehicleAmount(String passId, String version, Integer realVehicleType, Integer ifRoundOff,Integer isFee) {
        ResultMsg result = new ResultMsg();
        Integer newDataByPassId = exitPassDataMapper.getNewDataByPassId(passId);//去中间表（CD_JC.JC_NEWDATAOFPASSID）查询是否计算过大车小标
        Map<String, Object> conditions=new HashMap();
        conditions.put("passid",passId);
        if (newDataByPassId <= 0) {
           // List<Map<String, Object>> transactionPasstuDetailInfoList = transactionPasstuDetailMapper.getTransactionPasstuDetailInfo(conditions);
            exitPassDataMapper.insertTransactionPasstuDetail(passId);
        }
        List<Map<String, Object>> dataByPassIdList = transactionPasstuDetailMapper.getTransactionPasstuDetailInfo(conditions);//获取 门架可扣费交易 数据根据PassId
        List<JCNewDataOfPassId> newList = new ArrayList<>();//存放每次大车小标后的jcNewDataOfPassId对象到大车小标备份表中 CD_JC.JC_NEWDATAOFPASSID
        double realFee1 = 0.0;
        double oweFee1=0.0;
        for (Map<String, Object> map : dataByPassIdList) {
            String tollintervalids = map.get("TOLLINTERVALID").toString();//获取收费单元编号，多个收费单元用|分开
           double realFee = techFeerateIntervalMapper.getActualFeeByVersion(tollintervalids.split("\\|"), realVehicleType, version);//获取通行费用 根据收费单元、旧的收费实际车型和新的收费实际车型、实收金额计算
            BigDecimal bg1 = new BigDecimal(realFee);
            realFee = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
           // double oweFee = realFee - Double.parseDouble(map.get("PAYFEE").toString());
         /*   if (ifRoundOff == 1) {
                BigDecimal b = new BigDecimal((oweFee / 100));
                b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
                oweFee = b.multiply(new BigDecimal(100)).doubleValue();
            } else {
                BigDecimal bg = new BigDecimal(oweFee);
                oweFee = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }*/
            realFee1+=realFee;
        }
        Map map1 = exitPassDataMapper.getMultiprovince(passId);
        if("0".equals(map1.get("MULTIPROVINCE").toString())){
            oweFee1 = realFee1 - Double.parseDouble(map1.get("PAYFEE").toString());
        }else{
            Double tollFee = exitPassDataMapper.getFeeSP(passId);
            if(tollFee==null){
                tollFee=0.0;
            }
            oweFee1 = realFee1 - tollFee;
        }
        JCNewTradePassDate jcNewDataOfPassId = new JCNewTradePassDate();
        jcNewDataOfPassId.setRealFee(realFee1);
        jcNewDataOfPassId.setOweFee(oweFee1);
        //jcNewDataOfPassId.setOldVehicleType(map.get("VEHICLETYPE").toString());
        jcNewDataOfPassId.setNewVehicleType(realVehicleType);
        jcNewDataOfPassId.setPassId(passId);
        if(isFee==1){
            Map params=new HashMap();
            params.put("version",version);
            params.put("realVehicleType",realVehicleType);
            params.put("passid",passId);
            Map feeMap = exitPassDataMapper.selectMinFee(params);
            if(feeMap!=null) {

                jcNewDataOfPassId.setOwerFee(feeMap.get("OWERFEE") == null ? 0 : Double.parseDouble(feeMap.get("OWERFEE").toString()));
                jcNewDataOfPassId.setAllFee(feeMap.get("ALLFEE") == null ? 0 : Double.parseDouble(feeMap.get("ALLFEE").toString()));
            }
        }
        exitPassDataMapper.updateDataByPassId(jcNewDataOfPassId);//将计算打车小标后的数据存放到中间表中，用于出口通行数据查询关联查询
        result.setSuccess(true);
        result.setMessage("计算完毕！");
        return result;
    }
}
