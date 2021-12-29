package com.tuozhi.zhlw.admin.jc.service.impl;


import com.tuozhi.zhlw.admin.jc.entity.BlackListAttachment;

import com.tuozhi.zhlw.admin.jc.mapper.BlackListAttachmentMapper;

import com.tuozhi.zhlw.admin.jc.service.BlackListAttachmentService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListAttachmentServiceImpl implements BlackListAttachmentService {
	@Autowired
    private BlackListAttachmentMapper blackListAttachmentMapper;

	@Override
	public int saveBlacklistAttachment(BlackListAttachment blacklistAttachment) {
		return blackListAttachmentMapper.saveBlacklistAttachment(blacklistAttachment);
		
	}
   
}
