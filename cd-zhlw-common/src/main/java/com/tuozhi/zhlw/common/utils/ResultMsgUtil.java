package com.tuozhi.zhlw.common.utils;

import com.tuozhi.zhlw.common.bean.MessageCode;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;

/**
 * @author linqi
 * @create 2019/09/03 16:15
 **/
public class ResultMsgUtil {

    public static ResultMsg isSuccess(ResultMsgEnum resultMsgEnum) {
        return isSuccess(resultMsgEnum,null,null);
    }

    public static ResultMsg isSuccess(ResultMsgEnum resultMsgEnum, Object data) {

        return isSuccess( resultMsgEnum, data,null);
    }
    public static ResultMsg isSuccess(ResultMsgEnum resultMsgEnum,Object data,Object obj) {
        ResultMsg r= new ResultMsg();
        r.setSuccess(true);
        r.setMessage(resultMsgEnum.getMsg());
        r.setData(data);
        r.setObj(obj);
        return r;
    }
    public static ResultMsg isError(ResultMsgEnum resultMsgEnum, Object data) {
        ResultMsg r= new ResultMsg();
        r.setSuccess(false);
        r.setMessage(resultMsgEnum.getMsg());
        r.setData(data);
        return r;
    }
    public static ResultMsg isError(ResultMsgEnum resultMsgEnum) {
        return isError(resultMsgEnum,null);
    }
}
