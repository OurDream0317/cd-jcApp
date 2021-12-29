package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

@Data
public class JCNewTradePassDate {

    private Double realFee;  //应收金额
    private Double oweFee;   //欠费金额
    private Integer newVehicleType; //大车小标前的车型
    private String passId;  //通行ID
    private Double owerFee;//本省最小通行费
    private Double allFee; //全省最小通行费
}
