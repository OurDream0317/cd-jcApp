package com.tuozhi.zhlw.admin.jc.mapper;


import java.util.List;
import java.util.Map;

/**
 * @author jxc
 * @create 2020-01-05 11:36
 **/


public interface BlackListRewardMapper {
    /**
               * 获取 黑名单添加申请奖励数据
     *
     * @param condition
     * @return
     */
    List<Map<String,Object>> getBlackListRewardDataByCondition(Map<String,Object> condition);
    Long getBlackListRewardTotalCountByCondition(Map<String,Object> condition);

}
