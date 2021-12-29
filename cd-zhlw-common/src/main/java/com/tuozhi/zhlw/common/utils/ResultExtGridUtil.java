package com.tuozhi.zhlw.common.utils;

import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;

import java.util.List;

/**
 * @author linqi
 * @create 2019/09/06 23:30
 **/
public class ResultExtGridUtil {

    public static ResultExtGrid isSuccess(ResultMsgEnum resultMsgEnum, List root,Long total) {
        ResultExtGrid r= new ResultExtGrid();
        r.setSuccess(true);
        r.setMessage(resultMsgEnum.getMsg());
        r.setRoot(root);
        r.setTotalProperty(total);
        return r;
    }
    public static ResultExtGrid isSuccess(ResultMsgEnum resultMsgEnum, List root,Long total,Object sumMoney) {
        ResultExtGrid r= new ResultExtGrid();
        r.setSuccess(true);
        r.setMessage(resultMsgEnum.getMsg());
        r.setRoot(root);
        r.setTotalProperty(total);
        r.setSpecial(sumMoney==null?0.00:sumMoney);
        return r;
    }


    public static ResultExtGrid isError(ResultMsgEnum resultMsgEnum) {
        ResultExtGrid r= new ResultExtGrid();
        r.setSuccess(false);
        r.setMessage(resultMsgEnum.getMsg());
        r.setRoot(null);
        r.setTotalProperty(null);
        return r;
    }
    public static ResultExtGrid isSuccess(ResultMsgEnum resultMsgEnum) {
        ResultExtGrid r= new ResultExtGrid();
        r.setSuccess(true);
        r.setMessage(resultMsgEnum.getMsg());
        return r;
    }
}
