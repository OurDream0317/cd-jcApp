package com.tuozhi.zhlw.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.mapper.ContainerDao;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("container")
public class ContainerInfoController {

    private final ContainerDao dao;

    public ContainerInfoController(ContainerDao dao) {
        this.dao = dao;
    }

    @RequestMapping("containerInfo")
    public ResultExtGrid containerInfo(@RequestParam Map<String, String> params) {

        String vehicleId = params.get("vehicleId");
        String startTime = params.get("startTime");
        String endTime = params.get("endTime");

        generatePageParamsDefault(params);

        PageHelper.startPage(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("limit")));
        PageInfo<Map<String, String>> containerRecordList = new PageInfo<>(dao.findContainerRecord(vehicleId, startTime, endTime));


        ResultExtGrid resultExtGrid = new ResultExtGrid();
        resultExtGrid.setRoot(containerRecordList.getList());
        resultExtGrid.setSuccess(true);
        resultExtGrid.setTotalProperty(containerRecordList.getTotal());

        return resultExtGrid;
    }

    @RequestMapping("findPasstuSplitFee")
    public List<Map<String, String>> findPasstuSplitFee(String passId) {

        List<Map<String, String>> resultList = new LinkedList<>();

        Map<String, String> passTdMap = dao.findSplitPassTdByPassId(passId);

        Map<String, String> PTSDMap = dao.findSplitExitPtSdByPassId(passId);

        resultList.add(passTdMap);
        resultList.add(PTSDMap);

        return resultList;
    }

    @RequestMapping("containerPackageInfo")
    public ResultExtGrid containerPackageInfo(@RequestParam Map<String, String> params) {

        generatePageParamsDefault(params);

        PageHelper.startPage(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("limit")));
        PageInfo<Map<String, String>> containerRecordList = new PageInfo<>(dao.findContainerPackageList());

        ResultExtGrid resultExtGrid = new ResultExtGrid();
        resultExtGrid.setRoot(containerRecordList.getList());
        resultExtGrid.setSuccess(true);
        resultExtGrid.setTotalProperty(containerRecordList.getTotal());

        return resultExtGrid;
    }

    @RequestMapping("ContainerCheckRInfo")
    public ResultExtGrid ContainerCheckRInfo(@RequestParam Map<String, String> params) {

        generatePageParamsDefault(params);

        PageHelper.startPage(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("limit")));
        PageInfo<Map<String, String>> containerRecordList = new PageInfo<>(dao.findContainerCheckRList());

        ResultExtGrid resultExtGrid = new ResultExtGrid();
        resultExtGrid.setRoot(containerRecordList.getList());
        resultExtGrid.setSuccess(true);
        resultExtGrid.setTotalProperty(containerRecordList.getTotal());

        return resultExtGrid;
    }

    private void generatePageParamsDefault(@RequestParam Map<String, String> params) {
        String page = params.get("page");
        String limit = params.get("limit");
        params.put("page", (page == null || page.length() == 0) ? "1" : page);
        params.put("limit", (limit == null || limit.length() == 0) ? "20" : limit);
    }

}
