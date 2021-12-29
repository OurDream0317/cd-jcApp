package com.tuozhi.zhlw.admin.jc.service;

import java.util.List;

import com.tuozhi.zhlw.admin.jc.entity.tool.TollStation;

public interface TransactionDataService {

    //查询出所有收费站的信息
    List<TollStation> selectTollStation();
}
