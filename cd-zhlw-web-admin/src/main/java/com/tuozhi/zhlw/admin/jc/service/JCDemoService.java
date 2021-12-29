package com.tuozhi.zhlw.admin.jc.service;

import com.tuozhi.zhlw.common.bean.ResultMsg;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface JCDemoService {
    void selectDemo(HttpServletResponse response);
    void selectGreyAdd(HttpServletResponse response,Integer issuer);
    void selectJCMisstollVehicletype(HttpServletResponse response);
    void huizhong(HttpServletResponse response) throws Exception;
    void dropData();
    void selectData() throws IOException;
    ResultMsg getBase64ImageByVehicle(String vehicle);
}
