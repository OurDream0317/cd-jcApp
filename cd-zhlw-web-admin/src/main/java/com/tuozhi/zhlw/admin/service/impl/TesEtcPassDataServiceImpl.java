package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.entity.TesEtcPassDataEntity;
import com.tuozhi.zhlw.admin.mapper.TesEtcPassDataMapper;
import com.tuozhi.zhlw.admin.service.TesEtcPassDataService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * @author liyuyan
 *
 */
@Service
public class TesEtcPassDataServiceImpl implements TesEtcPassDataService {

	@Resource
	TesEtcPassDataMapper etcpassDataMapper;

	/**
	 * 根据协查编号、证据来源查找嫌疑数据
	 * @param sid
	 * @param evidence
	 * @return
	 */
	@Override
	public List<TesEtcPassDataEntity> findDataBySid(String sid, String evidence) {
		List<TesEtcPassDataEntity> etcPassDataEntities= null;
		if("0".equals(evidence)){
			etcPassDataEntities = etcpassDataMapper.findBySid(Long.parseLong(sid)); //嫌疑数据
		}else if("1".equals(evidence)){
			etcPassDataEntities = etcpassDataMapper.findPassDataBySid(Long.parseLong(sid)); //出口ETC通行数据
		}else if("2".equals(evidence)){
			etcPassDataEntities = etcpassDataMapper.findSlotCardBySid(Long.parseLong(sid)); //出口刷卡交易数据
		}else if("3".equals(evidence)){
			etcPassDataEntities = etcpassDataMapper.findTotherDataBySid(Long.parseLong(sid));//出口其他交易数据
		}else if("4".equals(evidence)){
			etcPassDataEntities = etcpassDataMapper.findEntryDataBySid(Long.parseLong(sid)); //入口通行交易数据
		}
		return etcPassDataEntities;
	}
}
