package com.tuozhi.zhlw.admin.jc.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.common.bean.QueryParams;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface TransactionPasstuDetailService {
     List<Map<String, Object>> getTransactionPasstuDetailInfo( Map<String, Object> conditions);
     Long selectAllDataCount(Map<String, Object> conditions);
     void excelTransactionPasstuDetailInfo(QueryParams queryParams, HttpServletResponse response,Map<String, Object> conditions) throws Exception;
}
