package com.tuozhi.zhlw.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.mapper.DifferentiationDao;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 津警车优惠查询
 */
@RestController
@RequestMapping("differentiation")
public class JinPoliceController {

    private final DifferentiationDao dao;

    public JinPoliceController(DifferentiationDao dao) {
        this.dao = dao;
    }

    @RequestMapping("jinPoliceQuery")
    public ResultExtGrid jinPoliceQuery(@RequestParam Map<String, String> params) {

        String cardId = params.get("cardId");

        String page = params.get("page");
        String limit = params.get("limit");
        page = (page == null || page.length() == 0) ? "1" : page;
        limit = (limit == null || limit.length() == 0) ? "20" : limit;

        PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
        PageInfo<Map<String, String>> containerRecordList = new PageInfo<>(dao.findJinPoliceList(cardId));

        ResultExtGrid resultExtGrid = new ResultExtGrid();
        resultExtGrid.setRoot(containerRecordList.getList());
        resultExtGrid.setSuccess(true);
        resultExtGrid.setTotalProperty(containerRecordList.getTotal());

        return resultExtGrid;
    }

}
