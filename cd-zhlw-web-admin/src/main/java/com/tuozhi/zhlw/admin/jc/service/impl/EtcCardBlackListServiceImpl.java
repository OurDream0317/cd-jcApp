package com.tuozhi.zhlw.admin.jc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.mapper.EtcCardBlackListMapper;
import com.tuozhi.zhlw.admin.jc.service.EtcCardBlackListService;
import com.tuozhi.zhlw.admin.jc.util.CarClassEnum;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EtcCardBlackListServiceImpl implements EtcCardBlackListService {
	@Resource
    private EtcCardBlackListMapper etcCardBlackListMapper;


	@Override
	public List<Map<String, Object>> findAll( Map<String, Object> selectMap) {
		List<Map<String, Object>> etcCardBlackLists=etcCardBlackListMapper.findAll(selectMap);
		return etcCardBlackLists;
	}

	@Override
	public Long selectAllDataCount(Map<String, Object> selectMap) {
		return etcCardBlackListMapper.selectAllDataCount(selectMap);
	}


	@Override
	public ResultMsg findEnumCode() {
		ResultMsg result = new ResultMsg();
        List<Map<String, Object>> enumCode = etcCardBlackListMapper.findEnumCode();

        result.setSuccess(true);
        result.setData(enumCode);
        result.setTotalProperty(enumCode.size());
        result.setMessage("查询成功");
        return result;
	}


	@Override
	public PageInfo findPicture(Map<String, Object> conditions,
			Map<String, Object> selectMap) {
		String pageno=conditions.get("pageno").toString();
		String pagesize=conditions.get("pagesize").toString();
		int pagenoint=Integer.parseInt(pageno);
		int pagesizeint=Integer.parseInt(pagesize);
		PageHelper.startPage(pagenoint,pagesizeint);
		List<Map<String, Object>> pictures=etcCardBlackListMapper.findPicture(selectMap);
		return new PageInfo<>(pictures);
	}


	@Override
	public ResultMsg<List<List<String>>> exportEtcCardApply(Map<String, Object> selectMap,String workflowdeptrole) {
		ResultMsg result = new ResultMsg();
		List<Map<String, Object>> etcCardBlackLists=etcCardBlackListMapper.findAll(selectMap);
		//添加导出Excle的内容
        List<List<String>> data = new ArrayList<>();
        for (Map<String, Object> map : etcCardBlackLists) {
        	List<String> etcCardRequest3 = new ArrayList<>();
        	etcCardRequest3.add(map.get("REQUESTORDERID") == null?"":map.get("REQUESTORDERID").toString());
        	etcCardRequest3.add(map.get("SALEDATE") == null?"":map.get("SALEDATE").toString());
			etcCardRequest3.add(map.get("AECRSALEDATE") == null?"":map.get("AECRSALEDATE").toString());
			etcCardRequest3.add(map.get("AEOCRSALEDATE") == null?"":map.get("AEOCRSALEDATE").toString());
        	etcCardRequest3.add(map.get("CPUCARDID") == null?"":map.get("CPUCARDID").toString());
        	etcCardRequest3.add(map.get("OIID") == null?"":map.get("OIID").toString());
			etcCardRequest3.add(map.get("OIIDTEMP") == null?"":map.get("OIIDTEMP").toString());
        	etcCardRequest3.add(map.get("CODENAME") == null?"":map.get("CODENAME").toString());
			etcCardRequest3.add(map.get("OFFICENAME") == null?"":map.get("OFFICENAME").toString());
			etcCardRequest3.add(map.get("USERNAME") == null?"":map.get("USERNAME").toString());
        	etcCardRequest3.add(map.get("PLATENO") == null?"":map.get("PLATENO").toString());
        	etcCardRequest3.add(map.get("CARDVEHICLECOLOR") == null?"":map.get("CARDVEHICLECOLOR").toString());
        	etcCardRequest3.add(map.get("CARDVEHICLETYPE") == null?"":map.get("CARDVEHICLETYPE").toString());
			etcCardRequest3.add(map.get("OICARUSERTYPE") == null?"": CarClassEnum.matchClass(Integer.parseInt(map.get("OICARUSERTYPE").toString())));
			etcCardRequest3.add(map.get("OICARTONNAGE") == null?"":map.get("OICARTONNAGE").toString());
			etcCardRequest3.add(map.get("OICARAXIES") == null?"":map.get("OICARAXIES").toString());
			etcCardRequest3.add(map.get("OICARLENGTH") == null?"":map.get("OICARLENGTH").toString());
			etcCardRequest3.add(map.get("OICARWIDTH") == null?"":map.get("OICARWIDTH").toString());
        	etcCardRequest3.add(map.get("OIHEADSTOCKHEIGHT") == null?"":map.get("OIHEADSTOCKHEIGHT").toString());
        	etcCardRequest3.add(map.get("CARDSTATE") == null?"":map.get("CARDSTATE").toString());
        	etcCardRequest3.add(map.get("CARDBLACKLISTTYPE") == null?"":map.get("CARDBLACKLISTTYPE").toString());
        	etcCardRequest3.add(map.get("CREATEDATE") == null?"":map.get("CREATEDATE").toString());
        	etcCardRequest3.add(map.get("OIPLATENO") == null?"":map.get("OIPLATENO").toString());
        	etcCardRequest3.add(map.get("OIPLATECOLOR") == null?"":map.get("OIPLATECOLOR").toString());
        	etcCardRequest3.add(map.get("OICARCATEGORY") == null?"":map.get("OICARCATEGORY").toString());
        	etcCardRequest3.add(map.get("OISTATE") == null?"":map.get("OISTATE").toString());
        	etcCardRequest3.add(map.get("OWNERNAME") == null?"":map.get("OWNERNAME").toString());
			if(workflowdeptrole!=null&&Integer.parseInt(workflowdeptrole)==1) {
				etcCardRequest3.add(map.get("CIMOBILE") == null?"":map.get("CIMOBILE").toString());
			}
        	data.add(etcCardRequest3);
		}
        result.setSuccess(true);
        result.setData(data);
        result.setMessage("查询成功");
        return result;
	}

	

	
	
	
   
}
