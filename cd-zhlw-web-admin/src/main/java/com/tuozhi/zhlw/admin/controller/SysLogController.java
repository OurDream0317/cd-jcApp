package com.tuozhi.zhlw.admin.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.dao.SysSqlManagerDao;
import com.tuozhi.zhlw.admin.entity.SysLogEntity;
import com.tuozhi.zhlw.admin.service.SysLogService;
import com.tuozhi.zhlw.admin.service.SysSqlManagerService;
import com.tuozhi.zhlw.admin.service.impl.SysLogServiceImpl;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import com.tuozhi.zhlw.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 日志处理
 * @Author ma_zy
 * @Date Created in 2019/10/8
 * @Modified by
 */
@RestController
@RequestMapping("/lookLog")
@Slf4j
public class SysLogController extends BaseController {


    @Autowired
    private SysLogService sysLogService;

    /**
     * 查询所有日志
     *
     * @param queryParams 分页参数
     * @return grid类型返回集
     */
    @RequestMapping("/findAll")
    public ResultExtGrid findAll(QueryParams queryParams,
                                 @RequestParam(name = "startTime",required = false) String startTime,
                                 @RequestParam(name = "endTime",required = false) String endTime,
                                 @RequestParam(name = "loginName",required = false) String loginName) {
        PageInfo<SysLogEntity> sysLogEntityListInfo = null ;
        try {
            sysLogEntityListInfo = this.sysLogService.findAllByPageHelper(queryParams,startTime,endTime,loginName) ;
        } catch (Exception e) {
            log.error(ResultMsgEnum.QUERY_ERROR.getMsg(), e);
            return ResultExtGridUtil.isError(ResultMsgEnum.ERROR) ;
        }
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, sysLogEntityListInfo.getList(), (long) sysLogEntityListInfo.getTotal());

    }
    @Resource
    SysSqlManagerService sysSqlManagerService;

    @RequestMapping("/data")
    public String data() {
        sysSqlManagerService.dataTrans();
        return "ok";
    }

    /**
     * 访问菜单log记录
     * @param preMenuName 父节点
     * @param menuName 当前菜单
     * @return
     */
    @RequestMapping("/menulog")
    public String menuVisitLog(@RequestParam(name = "menuId",required = true) String menuId,
                               @RequestParam(name = "menuName",required = true) String menuName, HttpServletRequest request) {

        if (StringUtils.isNotEmpty(menuName) && StringUtils.isNotEmpty(menuId)) {
//            String[] split = menuName.split("</span>");
//            menuName = split[0].substring(split[0].indexOf(">") + 1);
            String remark = "访问菜单：" + menuName + ", ID: " + menuId;
            sysLogService.insertMenuLog(getLoginUser(request),remark,request);
        }
        return "ok";

    }


}
