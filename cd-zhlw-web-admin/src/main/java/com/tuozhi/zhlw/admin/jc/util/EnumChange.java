package com.tuozhi.zhlw.admin.jc.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tuozhi.zhlw.admin.entity.SysEnumDetailsEntity;
import com.tuozhi.zhlw.admin.service.EnumManageService;
@Component
public class EnumChange {
	 @Autowired
	 EnumManageService service;
	 //获取车种
	 public  String codeChangeTextByEnumName(String enumName,String code) {
	        String text = "";
	        List<SysEnumDetailsEntity> list=service.findByEnumName(enumName);
	        for(SysEnumDetailsEntity e :list) {
	        	if(e.getEnumValue().equals(code)) {
	        		text = e.getEnumName();
	        		break;
	        	}
	        }
	        return text;
	 }
}
