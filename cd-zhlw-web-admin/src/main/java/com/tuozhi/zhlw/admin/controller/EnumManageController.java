package com.tuozhi.zhlw.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.dao.impl.SysSqlManagerDaoImpl;
import com.tuozhi.zhlw.admin.entity.SysEnumDetailsEntity;
import com.tuozhi.zhlw.admin.entity.SysEnumEntity;
import com.tuozhi.zhlw.admin.jc.service.TradePassService;
import com.tuozhi.zhlw.admin.service.EnumManageService;
import com.tuozhi.zhlw.admin.service.SysSqlManagerService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/07 15:50
 **/

@RestController
@RequestMapping("/JC/APP")
@Slf4j
public class EnumManageController extends BaseController{

    @Autowired
    EnumManageService service;

    @Autowired
    SysSqlManagerService sysSqlManagerService;
    
    @Autowired
    SysSqlManagerDaoImpl sysSqlManagerDao;
    
    @Autowired
    private TradePassService tradePassService;

    @RequestMapping("/findAll")
    public ResultExtGrid findAll(QueryParams queryParams,
                                 @RequestParam(name = "enumName") String enumName,
                                 @RequestParam(name = "orfieldvalue") String orfieldvalue,
                                 @RequestParam(name = "querymodel") String querymodel) {
        // 点击自定义查询中的‘查询统计’按钮之后 querymodel赋值为"dialog"
        String realEnumName = "dialog".equals(querymodel) ? enumName : orfieldvalue ;
        PageInfo<SysEnumEntity> enumPageInfo = service.findAllByPageInfo(queryParams,realEnumName);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, enumPageInfo.getList(), enumPageInfo.getTotal());
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST, params = "flag!=update" )
    public ResultExtGrid save(HttpServletRequest request, SysEnumEntity sysEnumEntity) {
        String enumManageDetails = request.getParameter("EnumManageDetails");
        log.debug(enumManageDetails);
        try {
            service.saveEnumAndDetails(sysEnumEntity,enumManageDetails);
        } catch (Exception e) {
          log.error(ResultMsgEnum.SAVE_ERROR.getMsg(),e);
          return ResultExtGridUtil.isError(ResultMsgEnum.SAVE_ERROR);
        }
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SAVE_OK, null, 0L);
    }
    @RequestMapping(value = "/save" ,method = RequestMethod.POST,params = "flag=update")
    public ResultExtGrid update(HttpServletRequest request, SysEnumEntity sysEnumEntity) {
        if (sysEnumEntity.getId() == null) {
            return ResultExtGridUtil.isError(ResultMsgEnum.UPDATE_ERROR);
        }

        SysEnumEntity targetEnum = service.selectByKey(sysEnumEntity.getId());
        BeanUtils.copyProperties(sysEnumEntity,targetEnum);

        String enumManageDetails = request.getParameter("EnumManageDetails");
        log.info(enumManageDetails);
        try {
            service.updateEnumAndDetails(sysEnumEntity,enumManageDetails);
        } catch (Exception e) {
            log.error(ResultMsgEnum.UPDATE_ERROR.getMsg(),e);
            return ResultExtGridUtil.isError(ResultMsgEnum.UPDATE_ERROR);
        }
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.UPDATE_OK, null, 0L);
    }

    @RequestMapping("/findAllEnumManageDetailsByEnumId")
    public ResultExtGrid findAllEnumManageDetailsByEnumId(String enumId) {
        if (StringUtils.isEmpty(enumId)) {
            return ResultExtGridUtil.isError(ResultMsgEnum.ERROR);
        }
        List<SysEnumDetailsEntity> enumDetailsEntities = service.findAllByEnumId(Long.valueOf(enumId));
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,enumDetailsEntities,
                Long.valueOf(enumDetailsEntities.size()));
    }
     @RequestMapping("/findPredefineSQLStore")
     public ResultExtGrid findPredefineSQLStore(HttpServletRequest req,String enumId){
          String sql=null;
          //如果是路段与门架联动时 enumId是该预定义数据的sid与选择的路段数据
          if(enumId.contains(";")){
              /*final List<String> tollgrantryIds = tradePassService.getTollgrantryIds(enumId.split(",")[1]);
              StringUtils.join(tollgrantryIds.toArray(),",");*/
                  String s = enumId.split(";")[1];
                  JSONArray ja = new JSONArray();
                  for (String s1 : s.split(",")) {
                      ja.add("\'" + s1 + "\'");
                  }
             if(enumId.split(";")[2].equals("2")){//收费站
                 sql = "SELECT (select to_char(wm_concat(id)) from cd_pass.BASE_TOLLSTATION where  SECTIONID in (" + StringUtils.join(ja, ",") + ")) as ENUMVALUE,'所有' as ENUMNAME,'' as SECTIONID   from dual union all SELECT * from  (" + sysSqlManagerService.getBySid(enumId.split(";")[0]) + ") where  SECTIONID in (" + StringUtils.join(ja, ",") + ")";
              }else {//门架
                 sql = "SELECT (select to_char(wm_concat(id)) from cd_pass.BASE_TOLLPOINT where  SECTIONID in (" + StringUtils.join(ja, ",") + ")) as ENUMVALUE,'所有' as ENUMNAME,'' as SECTIONID   from dual union all SELECT * from  (" + sysSqlManagerService.getBySid(enumId.split(";")[0]) + ") where  SECTIONID in (" + StringUtils.join(ja, ",") + ")";

             }}else {
              String paramScriptSource = sysSqlManagerService.getBySid(enumId);
              if (StringUtils.isEmpty(paramScriptSource)) {
                  return ResultExtGridUtil.isError(ResultMsgEnum.ERROR);
              }
              if (paramScriptSource.equals("@luduan_role@")) {
                  if (getLoginUser(req).getDeptId() == 1||getLoginUser(req).getDeptId() == 3) {
                      sql = "SELECT '' ENUMVALUE,'所有' as ENUMNAME from dual union all \n" +
                              "SELECT ENUMVALUE,ENUMNAME FROM (\n" +
                              "SELECT\n" +
                              "\t\tb.ID AS ENUMVALUE,\n" +
                              "\t\tb.NAME AS ENUMNAME \n" +
                              "\tFROM\n" +
                              "\t\tCD_PASS.BASE_SECTION b \n" +
                              "ORDER BY\n" +
                              "\tb.order_idx)";
                  } else {
                      if (getLoginUser(req).getWorkFlowDeptRole().equals("2")) {
                          sql = "SELECT\n" +
                                  "\tsqlbs.ID AS ENUMVALUE,\n" +
                                  "\tsqlbs.Name AS ENUMNAME \n" +
                                  "FROM\n" +                                  "\tCD_SYSTEM.SYS_DEPT sqlsd\n" +
                                  "\tLEFT JOIN CD_PASS.BASE_SECTION sqlbs ON sqlsd.DEPT_LONG_ID = sqlbs.ID \n" +
                                  "WHERE\n" +
                                  "\tsqlsd.WORKFLOWDEPTROLE = 2 CONNECT BY PRIOR sqlsd.ID = sqlsd.PARENT_ID START WITH sqlsd.ID =" + getLoginUser(req).getDeptId();
                      }
                      else if (getLoginUser(req).getWorkFlowDeptRole().equals("8")) {
                          sql = "SELECT\n" +
                                  "\t(\n" +
                                  "SELECT\n" +
                                  "\tto_char(wm_concat ( ID )) \n" +
                                  "FROM\n" +
                                  "\tCD_PASS.BASE_SECTION sqlbs \n" +
                                  "WHERE\n" +
                                  "sqlbs.SECTIONOWNERID IN ( SELECT DEPT_WORK FROM cd_system.SYS_DEPT WHERE  ID =" + getLoginUser(req).getDeptId()+" )) AS ENUMVALUE,\n" +
                                  "\t'所有' AS ENUMNAME \n" +
                                  "FROM dual\n" +
                                  "\tUNION ALL\n" +
                                  "SELECT\n" +
                                  "\t* \n" +
                                  "FROM\n" +
                                  "\t(\n" +
                                  "SELECT\n" +
                                  "\tsqlbs.ID AS ENUMVALUE,\n" +
                                  "\tsqlbs.Name AS ENUMNAME \n" +
                                  "FROM\n" +
                                  "\tCD_PASS.BASE_SECTION sqlbs \n" +
                                  "WHERE\n" +
                                  "\tsqlbs.SECTIONOWNERID IN ( SELECT DEPT_WORK FROM cd_system.SYS_DEPT WHERE  ID =" + getLoginUser(req).getDeptId()+") ORDER BY sqlbs.ORDER_IDX)";
                      }
                      else {
                          sql = "\n" +
                                  "SELECT\n" +
                                  "(SELECT\n" +
                                  "\tto_char(wm_concat(\n" +
                                  "\tsqlbs.SE_ID)) AS ENUMVALUE\n" +
                                  "FROM\n" +
                                  "\tCD_SYSTEM.SYS_DEPT sqlsd\n" +
                                  "\tLEFT JOIN CD_PASS.BASIC_SECTION sqlbs ON sqlsd.DEPT_LONG_ID = sqlbs.SE_ID WHERE\n" +
                                  "\tsqlsd.WORKFLOWDEPTROLE = 2 CONNECT BY PRIOR sqlsd.ID = sqlsd.PARENT_ID START WITH sqlsd.ID =\n" + getLoginUser(req).getDeptId() +
                                  ") AS ENUMVALUE,\n" +
                                  "\t'所有' AS ENUMNAME from dual\n" +
                                  "UNION ALL select * from (" +
                                  "SELECT\n" +
                                  "\tsqlbs.ID AS ENUMVALUE,\n" +
                                  "\tsqlbs.Name AS ENUMNAME \n" +
                                  "FROM\n" +
                                  "\tCD_SYSTEM.SYS_DEPT sqlsd\n" +
                                  "\tLEFT JOIN CD_PASS.BASE_SECTION sqlbs ON sqlsd.DEPT_LONG_ID = sqlbs.ID \n" +
                                  "WHERE\n" +
                                  "\tsqlsd.WORKFLOWDEPTROLE = 2 CONNECT BY PRIOR sqlsd.ID = sqlsd.PARENT_ID START WITH sqlsd.ID =" + getLoginUser(req).getDeptId() +
                                  " order by sqlbs.order_idx)";
                      }
                  }
              } else {
                  sql = paramScriptSource;
              }
          }
         List<Map> list=sysSqlManagerDao.findPredefineSQLStore(sql);
         return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,list,
                 Long.valueOf(list.size()));
     }
    @RequestMapping("/delete")
    public ResultMsg delete(Long enumId) {
        try {
            service.deleteEnumIdAndDetailsByEnumId(enumId);
        } catch (Exception e) {
            log.error(ResultMsgEnum.UPDATE_ERROR.getMsg(),e);
            return ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR);
        }
       return ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK);
    }

    @RequestMapping("/findAllEnumManageDetailsByEnumName")
    public ResultExtGrid findAllEnumManageDetailsByEnumName(@RequestParam("enumName") String enumName) {
        List<SysEnumDetailsEntity> enumDetailsList = service.findByEnumName(enumName);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS,enumDetailsList,(long)enumDetailsList.size());
    }


}
