package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.TradePassEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TradePassMapper {

    List<TradePassEntity> selectTradePass(Map<String, Object> conditions);

    List<Map<String, Object>> selectSection();
    List<Map<String, Object>> selectOwerStation(@Param("deptId") Long deptId);
    List<Map<String, Object>> selectStation();
    List<Map<String, Object>> selectStationByCondition(Map<String,Object> condition);

    //Map<String, Object> selectSection();

    /**
     * 获取 通行记录表 的数据
     *
     * @param tradePassEntity
     * @return
     */
    List<TradePassEntity> getDataByParam(Map<String, Object> conditions);


    long selectAllDataCount(Map<String, Object> conditions);

    /**
     * 获取特情 通行记录表 的数据
     *
     * @param tradePassEntity
     * @param isDispose
     * @return
     */
    List<TradePassEntity> getSpecialDataByParam(@Param("tradePassEntity") TradePassEntity tradePassEntity, @Param("isDispose") String isDispose,@Param("pageStart") Integer pageStart,@Param("pageEnd") Integer pageEnd);
    long selectAllSpecialDataCount(@Param("tradePassEntity") TradePassEntity tradePassEntity, @Param("isDispose") String isDispose);
    /**
     * 获取 通行记录表 的数据
     *
     * @param passId
     * @return
     */
    List<TradePassEntity> getDataByPassId(@Param("passId") String passId);

    /**
     * 获取 通行记录表 的数据根据多个passId
     *
     * @param passIdList
     * @return
     */
    List<TradePassEntity> getDataByPassIdList(@Param("passIdList") List<String> passIdList);

	Integer selectCount(Map<String, Object> conditions);

	List<TradePassEntity> selecTradePassListExport(Map<String, Object> conditions);
	Map selectByTradePass(@Param("passId") String passId);
	void insertJsonObject(@Param("m") Map m);
    Double getLnegthByTollIntervalIdList(@Param("tollIntervalIdList")List<String> tollIntervalIdList);
    String getNameById(@Param("id") String id);
    List<String> getTollgrantryIds(@Param("sectionid") String sectionid);//通过该路段编号下的所有门架编号
}
