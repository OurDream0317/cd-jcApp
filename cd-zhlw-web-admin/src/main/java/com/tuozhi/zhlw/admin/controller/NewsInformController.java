package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author linqi
 * @create 2019/11/16 17:48
 **/
@RestController
@RequestMapping("/news")
public class NewsInformController extends BaseController {

    @RequestMapping(value = "/findAllNewsInformByUserId", method = RequestMethod.POST)
    public ResultMsg findAllNewsInformByUserId(HttpServletRequest req) {

        LoginUser loginUser = getLoginUser(req);

        if (null == loginUser) {
         return ResultMsgUtil.isError(ResultMsgEnum.RETURN_LOGIN);
        }



        return ResultMsgUtil.isSuccess(ResultMsgEnum.SUCCESS);
    }
}
