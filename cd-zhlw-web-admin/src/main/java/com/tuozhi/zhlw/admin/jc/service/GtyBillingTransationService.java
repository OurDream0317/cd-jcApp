package com.tuozhi.zhlw.admin.jc.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.GtyBillingTransationEntity;
import com.tuozhi.zhlw.common.bean.QueryParams;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GtyBillingTransationService {

    List<GtyBillingTransationEntity> getGtyBillingTransationInfo(Map<String, Object> conditions);
    Long selectAllDataCount(Map<String, Object> conditions);
    PageInfo<GtyBillingTransationEntity> getGtyBillingTransationInfoByPassId(String passid, QueryParams queryParams);
    void newCreateExcel(Map<String, Object> conditions, HttpServletResponse response) throws Exception;
}
