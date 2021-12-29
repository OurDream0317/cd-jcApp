package com.tuozhi.zhlw.admin.jc.util;

public class FundsUtils {
    //向上发送
    public static final Integer UP = 2;
    //向下发送
    public static final Integer DOWN = 1;

    //代办
    public static final Integer AWAIT = 1;

    public static final String FLOW_PATH_NAME = "稽查经费请求";

    //经费请求通过办结
    public static final Integer REQUEST_PASS_CHECK = 2;
    //经费请求流程状态通过办结
    public static final Integer FLOW_PATH_PASS_CHECK = 3;

    //未通过
    public static final Integer REQUEST_NOT_PASS_CHECK = 3;

    public static final Integer FLOW_PATH_NOT_PASS_CHECK = 4;

    //被退回
    public static final Integer FLOW_PATH_BACK = 2;
}
