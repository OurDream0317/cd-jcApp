package com.tuozhi.zhlw.common.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author linqi
 * @create 2019/09/04 23:54
 **/

@Getter
public enum  ResultCodeEnum {
    SUCCESS(1,"成功") ,
    ERROR(9999,"未知错误") ,
     APPLICATION_ERROR(9000,"应用级错误"),
     VALIDATE_ERROR(9001,"参数验证错误"),
    SERVICE_ERROR(9002,"业务逻辑验证错误"),
    CACHE_ERROR(9003,"缓存访问错误"),
    DAO_ERROR(9004,"数据访问错误");

    private int code;
    private String msg;

    private ResultCodeEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
