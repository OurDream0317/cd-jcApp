package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.GtyBillingTransaction;
import com.tuozhi.zhlw.admin.jc.entity.JCNewDataOfPassId;
import com.tuozhi.zhlw.admin.jc.entity.TradeGantryEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TradeGantryMapper
 * @Descriotion TODO 门架可扣费交易Mapper接口
 * @Author fujiankang
 * @Date 2019/10/28 11:36
 * @Version 1.0
 */
public interface TradeGantryMapper {

    /**
     * 获取 门架可扣费交易 数据根据PassId
     * @param passId
     * @return
     */
    List<TradeGantryEntity> getDataByPassId(@Param("passId")String passId);

    /**
     * 只获取门架下的所有收费单元
     */
    List<String> getTollintervalByPassId(@Param("passId")String passId);
    /**
     * 获取 门架可扣费交易 数据根据多个id
     * @param ids
     * @return
     */
    List<TradeGantryEntity> getDataByids(@Param("ids")List<String> ids);

    List<TradeGantryEntity> getTradeGantryDataByPassId(@Param("passId") String passId);

    void insertTradeGantryData(@Param("tradeGantryDataList") List<TradeGantryEntity> tradeGantryDataList);

    void insertNewTradeGantryData(@Param("GtyBillingTransaction")List<GtyBillingTransaction> GtyBillingTransaction );
    void insertNewTransactionPassData(@Param("transactionMapList")List<Map<String,Object>> transactionMapList);
    void updateDataByPassId(@Param("newList") List<JCNewDataOfPassId> newList);
    /**
     * 获取 门架可扣费交易 数据根据id
     * @param id
     * @return
     */
    TradeGantryEntity getDataById(@Param("id")String id);

    Integer getNewDataByPassId(@Param("passId") String passId);

    List<Map<String,Object>> getVersion();









    List<Map<String,Object>> selectPassid();

    void updateData(Double oweFee,Double realfee,String passId);
}
