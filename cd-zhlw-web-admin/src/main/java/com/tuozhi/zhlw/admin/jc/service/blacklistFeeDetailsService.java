package com.tuozhi.zhlw.admin.jc.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.BlacklistDownAll;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultMsg;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface blacklistFeeDetailsService {
    PageInfo<List<Map<String,Object>>> findAll(QueryParams queryParams);
    /**
     * 导入Excel
     * @param fileName
     * @return
     */
    ResultMsg readBlackListExcelInfo(File fileName) throws ParseException;

    void onExportblacklistFeeDetailsExcel(HttpServletResponse response) throws Exception;

    int onDelblacklistFeeDetailsExcel(long[] ids);
}
