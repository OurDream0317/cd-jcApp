package com.tuozhi.zhlw.admin.jc.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.BlackListRequest;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface BlackListRequestService {

	PageInfo<BlackListRequest> findAllPage(Map<String, Object> conditions);

	Map<String,Object> getCountByVehicle(Map<String, Object> map);
	PageInfo<BlackListRequest> selectBlackListInform(Map<String, Object> conditions,QueryParams queryParams);
	
	int insertBlackListForm(BlackListRequest blackListRequest);
	
	boolean deleteBlackListInfo(List<Integer> integetlist);
	
	PageInfo<BlackListRequest> findAllById(Map<String, Object> conditions);
	
	boolean updateBlackList(BlackListRequest blackListRequest);
	
	int saveBlackListForm(BlackListRequest blackListRequest);
	
	PageInfo<BlackListRequest> findAllRevocation(Map<String, Object> conditions);

	/**
	 * 黑名单撤销申请-添加
	 * @param blackListRequest
	 * @return
	 */
	ResultMsg addBlackRevokedAndUpdateRoadOrCardBlackList(BlackListRequest blackListRequest, String title, LoginUser loginUser,String flag, MultipartFile[] file) throws IOException;


	ResultMsg addBlackRevoked(BlackListRequest blackListRequest, String title, LoginUser loginUser,String flag, MultipartFile[] file) throws IOException;
	/**
	 * 黑名单撤销申请-删除
	 * @param requestIds
	 * @return
	 */
	ResultMsg deleteBlackList(List<Long> requestIds);

	/**
	 * 查询收费站，根据name
	 * @param name
	 * @return
	 */
	ResultMsg getStationByName(String name);
	ResultMsg newGetStationByName(String name,Long deptId);
	/**
	 * 查询黑名单添加申请车辆
	 * @param carNumber
	 * @param carColour
	 * @return
	 */
	ResultMsg findRevokeBlackList(String carNumber,Integer carColour);
	/**
	 * 查询黑名单添加申请车辆
	 * @param carNumber
	 * @param carColour
	 * @return
	 */
	List<Map<String,Object>> searchBlackListNo(String carNumber,Integer carColour,Double eludeMoneyNumber);

}
