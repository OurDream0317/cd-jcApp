package com.tuozhi.zhlw.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.mapper.EspeciallyDao;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@RestController
@RequestMapping("especially")
public class EspeciallyManageController {

    private final EspeciallyDao dao;

    public EspeciallyManageController(EspeciallyDao dao) {
        this.dao = dao;
    }

    @RequestMapping("findAllEspecially")
    public ResultExtGrid AllEspecially(@RequestParam Map<String, String> params) {

        String vehicleId = params.get("vehicleId");
        String etcCardId = params.get("etcCardId");

        String page = params.get("page");
        String limit = params.get("limit");
        page = (page == null || page.length() == 0) ? "1" : page;
        limit = (limit == null || limit.length() == 0) ? "20" : limit;

        PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
        PageInfo<Map<String, String>> containerRecordList = new PageInfo<>(dao.findContainerRecord(vehicleId, etcCardId));


        ResultExtGrid resultExtGrid = new ResultExtGrid();
        resultExtGrid.setRoot(containerRecordList.getList());
        resultExtGrid.setSuccess(true);
        resultExtGrid.setTotalProperty(containerRecordList.getTotal());

        return resultExtGrid;
    }

    @RequestMapping("addEspecially")
    public ResultExtGrid saveEspecially(@RequestParam Map<String, Object> params) {
        ResultExtGrid resultExtGrid = new ResultExtGrid();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String vehicleId = params.get("vehicleId") + "_" + params.get("vehicleColor");
            Date startTime = simpleDateFormat.parse(params.get("startTime") + " 00:00:00");
            Date endTime = simpleDateFormat.parse(params.get("endTime") + " 23:59:59");
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("vehicleId", vehicleId);

            if (startTime.getTime() > endTime.getTime()) {
                resultExtGrid.setSuccess(false);
                resultExtGrid.setMessage("保存失败,开始时间不得大于结束时间!");
            } else {

                Map<String, String> plateMap = dao.findRecordByPlate(vehicleId);
                Map<String, String> cardIdMap = dao.findRecordByCardId((String) params.get("etcCarId"));

                if ((plateMap != null && !plateMap.isEmpty()) || (cardIdMap != null && !cardIdMap.isEmpty())) {
                    resultExtGrid.setSuccess(false);
                    resultExtGrid.setMessage("保存失败,要保存的数据已经存在!");
                } else {
                    
                    Integer count = dao.insertEspeciallyCar(params);
                    if (count > 0) {
                        resultExtGrid.setSuccess(true);
                        resultExtGrid.setMessage("保存成功!");
                    } else {
                        resultExtGrid.setSuccess(false);
                        resultExtGrid.setMessage("保存失败,检查要保存的数据是否正确!");
                    }
                }
            }
        } catch (ParseException e) {
            resultExtGrid.setSuccess(false);
            resultExtGrid.setMessage("保存失败,未知错误!");
            e.printStackTrace();
        }
        return resultExtGrid;
    }

    @RequestMapping("deleteEspecially")
    public ResultExtGrid deleteEspecially(@RequestParam("id") Long id) {
        ResultExtGrid resultExtGrid = new ResultExtGrid();

        try {
            Integer count = dao.deleteRecordById(id);
            if (count > 0) {
                resultExtGrid.setSuccess(true);
                resultExtGrid.setMessage("删除成功!");
            }
        } catch (Exception e) {
            resultExtGrid.setSuccess(false);
            resultExtGrid.setMessage("发生错误,请刷新页面重试!");
            e.printStackTrace();
        }

        return resultExtGrid;
    }

}
