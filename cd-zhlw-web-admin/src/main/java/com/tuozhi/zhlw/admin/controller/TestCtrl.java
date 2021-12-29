package com.tuozhi.zhlw.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author linqi
 * @create 2019/09/05 15:53
 **/

@Controller
public class TestCtrl {
    @RequestMapping("/")
    public String Test() {
        return "index";
    }

}
