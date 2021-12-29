package com.tuozhi.zhlw.admin.jc.service;

import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @ClassName HomePageService
 * @Descriotion TODO 首页Service接口
 * @Author liyuyan
 * @Date 2019/12/06 11:50
 * @Version 1.0
 */

public interface HomePageService {

    /**
     * 查询省协查请求申请数据
     * @param deptId
     * @return
     */
    ResultExtGrid findAssistantAll(String deptId, QueryParams queryParams);

    /**
     * 查询黑名单申请数据
     * @param deptId
     * @return
     */
    ResultExtGrid findBlackListRequest(Long deptId, QueryParams queryParams);

    /**
     * 查询稽查经费申请数据
     * @param deptId
     * @return
     */
    ResultExtGrid findFundsRequestAll(Long deptId, QueryParams queryParams);

}
