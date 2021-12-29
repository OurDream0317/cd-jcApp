package com.tuozhi.zhlw.admin.jc.service.impl;


import com.tuozhi.zhlw.admin.jc.entity.BlackListAttachment;
import com.tuozhi.zhlw.admin.jc.entity.BlackListFlowpath;
import com.tuozhi.zhlw.admin.jc.mapper.BlackListAttachmentMapper;
import com.tuozhi.zhlw.admin.jc.mapper.BlackListFlowpathMapper;
import com.tuozhi.zhlw.admin.jc.service.BlackListFlowpathService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListFlowpathServiceImpl implements BlackListFlowpathService {
	@Autowired
    private BlackListFlowpathMapper blackListFlowpathMapper;

	@Override
	public int saveBlacklistFlowpath(BlackListFlowpath blackListFlowpath) {
		
		return blackListFlowpathMapper.saveBlacklistFlowpath(blackListFlowpath);
	}
	
   
}
