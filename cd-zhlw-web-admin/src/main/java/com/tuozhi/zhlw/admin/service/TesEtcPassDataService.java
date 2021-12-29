package com.tuozhi.zhlw.admin.service;

import java.util.List;

import com.tuozhi.zhlw.admin.entity.TesAssistCheckEntity;
import com.tuozhi.zhlw.admin.entity.TesEtcDataEntity;
import com.tuozhi.zhlw.admin.entity.TesEtcPassDataEntity;
import com.tuozhi.zhlw.admin.entity.TesEtcTotherDataEntity;
import com.tuozhi.zhlw.common.bean.ResultMsg;

/**
 * 
 * @author liyuyan
 *
 */
public interface TesEtcPassDataService {

	List<TesEtcPassDataEntity> findDataBySid(String sid, String evidence);

}
