package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.GtyBillingTransaction;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAddLog;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAttachment;
import com.tuozhi.zhlw.admin.jc.entity.JcOperationLogEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wwx
 * @Date: 2019/12/2 12:08
 * @Description:
 */
@Repository
public interface GtyTradeMapper {




    List<GtyBillingTransaction> getCarTrade(Map map);
}
