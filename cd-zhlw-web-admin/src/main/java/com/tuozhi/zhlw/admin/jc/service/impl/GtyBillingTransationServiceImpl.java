package com.tuozhi.zhlw.admin.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.GtyBillingTransationEntity;
import com.tuozhi.zhlw.admin.jc.mapper.GtyBillingTransationInfoMapper;
import com.tuozhi.zhlw.admin.jc.service.GtyBillingTransationService;
import com.tuozhi.zhlw.admin.jc.util.ExcelUtils;
import com.tuozhi.zhlw.common.bean.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class GtyBillingTransationServiceImpl implements GtyBillingTransationService {
    @Autowired
    private GtyBillingTransationInfoMapper mapper;
    @Value("${fileDownPath}")
    private String fileDownPath;

    @Override
    public List<GtyBillingTransationEntity> getGtyBillingTransationInfo(Map<String, Object> conditions) {
        List<GtyBillingTransationEntity> list = mapper.getGtyBillingTransationInfo(conditions);
        return list;
    }

    @Override
    public Long selectAllDataCount(Map<String, Object> conditions) {
        return mapper.selectAllDataCount(conditions);
    }

    @Override
    public PageInfo<GtyBillingTransationEntity> getGtyBillingTransationInfoByPassId(String passid, QueryParams queryParams) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<GtyBillingTransationEntity> list = mapper.getGtyBillingTransationInfoByPassId(passid);
        return new PageInfo<GtyBillingTransationEntity>(list);
    }

    @Override
    public void newCreateExcel(Map<String, Object> conditions, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> list = mapper.newCreateExcel(conditions);
        String[] titles = {"通行编号", "门架编号", "门架名称","路段名称", "收费车辆号码", "通行介质类型", "OBU编号/CPC卡号", "ETC卡编号", "门架交易时间", "入口交易时间", "应收金额", "交易金额", "优惠金额", "卡面扣费金额", "OBU/双片标识","计费车型",  "入口车型","入口车道编号", "车种",
                "通行状态", "节假日状态", "卡片复合交易结果", "计费模块计费模式", "车牌识别流水号", "计费方式","PASSID"};
        String[] rows = {"TRADEID", "GANTRYID", "GANTRYNAME","SECTIONNAME", "VEHICLEPLATE", "MEDIATYPE", "OBUSN", "CPUCARDID", "TRANSTIME", "ENTIME", "PAYFEE", "FEE", "DISCOUNTFEE",
                "TRANSFEE", "OBUSIGN", "FEEVEHICLETYPE","VEHICLETYPE", "ENTOLLLANEID", "VEHICLECLASS", "PASSSTATE", "HOLIDAYSTATE", "TRADERESULT", "RATECOMPUTE", "VEHICLEPICID", "EXITFEETYPE","PASSID"};
         ExcelUtils.excelUtil(response, titles, list, rows, "原始门架交易数据");
    }
}
