package com.tuozhi.zhlw.admin.jc.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.GtyBillingTransaction;

import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author wwx
 * @Date: 2019/12/2 10:25
 * @Description:
 */
public interface GtyTradeService {
    PageInfo<GtyBillingTransaction> getCarTrade(QueryParams queryParams, String passid);

}
