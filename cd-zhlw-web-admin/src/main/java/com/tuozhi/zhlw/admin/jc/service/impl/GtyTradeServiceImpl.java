package com.tuozhi.zhlw.admin.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.GtyBillingTransaction;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAddLog;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAttachment;
import com.tuozhi.zhlw.admin.jc.entity.JcOperationLogEntity;
import com.tuozhi.zhlw.admin.jc.mapper.GtyTradeMapper;
import com.tuozhi.zhlw.admin.jc.mapper.JcLogMapper;
import com.tuozhi.zhlw.admin.jc.service.GtyTradeService;
import com.tuozhi.zhlw.admin.jc.service.JcLogService;
import com.tuozhi.zhlw.admin.jc.util.UpFileUtil;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author wwx
 * @Date: 2019/12/2 12:06
 * @Description:
 */
@Service
public class GtyTradeServiceImpl implements GtyTradeService {

    @Autowired
    private GtyTradeMapper gtyTradeMapper ;


    @Override
    public PageInfo<GtyBillingTransaction>  getCarTrade(QueryParams queryParams, String passid) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());

        Map map=new HashMap();
        map.put("passid",passid);


        List<GtyBillingTransaction> list=gtyTradeMapper.getCarTrade(map);
        return new PageInfo<GtyBillingTransaction>(list);



    }



}
