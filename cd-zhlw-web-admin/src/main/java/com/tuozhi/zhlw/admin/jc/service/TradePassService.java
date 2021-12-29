package com.tuozhi.zhlw.admin.jc.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.TradePassEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;

import java.util.List;
import java.util.Map;

public interface TradePassService {

    PageInfo<TradePassEntity> selectTradePass(Map<String, Object> conditions, QueryParams queryParams);

    List<Map<String, Object>> selectSection();
    List<Map<String, Object>> selectOwerStation(Long deptId);
    List<Map<String, Object>> selectStation();
    List<Map<String, Object>> selectStation(Map<String,Object> condition);

    /**
     * 获取 通行记录表 的数据根据参数
     *
     * @param tradePassEntity
     * @param queryParams
     * @return
     */
    List<TradePassEntity> getTradePassByParam(Map<String, Object> conditions);

    Long selectAllDataCount(Map<String, Object> conditions);

    /**
     * 获取特情 通行记录表 的数据根据参数
     *
     * @param tradePassEntity
     * @param isDisposes
     * @param queryParams
     * @param loginUser
     * @return
     */
    ResultExtGrid getSpecialTradePassByParam(TradePassEntity tradePassEntity, List<Integer> isDisposes, QueryParams queryParams, LoginUser loginUser,  Integer pageStart,Integer pageEnd);

    /**
     * 获取 多条 通行记录数据
     *
     * @param passIdList
     * @return
     */
    ResultMsg<List<List<String>>> getSpecialTradePassByPassIdList(List<String> passIdList);


    /***
     * 最大导出数量
     * */
    Integer selectCount(Map<String, Object> conditions);


    /**
     * 导出
     **/
    List<TradePassEntity> selecTradePassListExport(Map<String, Object> conditions);


    /**
     * cpc拟合
     * author:wwx
     */
    Map cpcFitting(String passIds);


    List<String> getTollgrantryIds(String sectionid);//通过该路段编号下的所有门架编号
}
