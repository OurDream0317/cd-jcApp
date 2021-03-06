package com.tuozhi.zhlw.admin.jc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysEnumDetailsEntity;
import com.tuozhi.zhlw.admin.jc.entity.JCGantryFitting;
import com.tuozhi.zhlw.admin.jc.entity.TradePassEntity;
import com.tuozhi.zhlw.admin.jc.mapper.BaseDeptMapper;
import com.tuozhi.zhlw.admin.jc.mapper.TradePassMapper;
import com.tuozhi.zhlw.admin.jc.service.TradePassService;
import com.tuozhi.zhlw.admin.jc.util.ConstUtil;
import com.tuozhi.zhlw.admin.jc.util.DateFormat;
import com.tuozhi.zhlw.admin.mapper.EnumDetailMapper;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class TradePassServiceImpl implements TradePassService {
    @Autowired
    TradePassMapper mapper;
    @Resource
    private BaseDeptMapper baseDeptMapper;
    @Resource
    private EnumDetailMapper enumDetailMapper;


    @Override
    public PageInfo<TradePassEntity> selectTradePass(Map<String, Object> conditions, QueryParams queryParams) {
        // TODO Auto-generated method stub
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<TradePassEntity> list = mapper.selectTradePass(conditions);
        return new PageInfo<TradePassEntity>(list);
    }


    @Override
    public List<Map<String, Object>> selectSection() {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = mapper.selectSection();
        return list;
    }


    @Override
    public List<Map<String, Object>> selectStation() {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = mapper.selectStation();
        return list;
    }
    @Override
    public List<Map<String, Object>> selectOwerStation(Long deptId) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = mapper.selectOwerStation(deptId);
        return list;
    }
    @Override
    public List<TradePassEntity> getTradePassByParam(Map<String, Object> conditions) {
        List<TradePassEntity> list = mapper.getDataByParam(conditions);
        return list;
    }

    @Override
    public Long selectAllDataCount(Map<String, Object> conditions) {
        return mapper.selectAllDataCount(conditions);
    }

    @Override
    public ResultExtGrid getSpecialTradePassByParam(TradePassEntity tradePassEntity, List<Integer> isDisposes, QueryParams queryParams, LoginUser loginUser,Integer pageStart,Integer pageEnd) {
        List<TradePassEntity> tradePassDataList = new ArrayList<>();
        //????????????
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        //????????????????????????????????????????????????
        List<String> deptLongIdList = null;
        String deptLongIdStrs = baseDeptMapper.getAllNextDeptDeptLongId(loginUser.getDeptId());
        if (deptLongIdStrs != null) {
            String[] deptLongIdStrArr = deptLongIdStrs.split(",");
            deptLongIdList = Arrays.asList(deptLongIdStrArr);
        }
        String isDispose = null;
        if (isDisposes.isEmpty()) {
            isDispose = null;
        } else if (isDisposes.contains(1) && isDisposes.contains(2)) {
            isDispose = null;
        } else if (isDisposes.contains(1)) {
            isDispose = "IS NOT";
        } else if (isDisposes.contains(2)) {
            isDispose = "IS";
        }
        //????????????
        List<TradePassEntity> tradePassEntityList = mapper.getSpecialDataByParam(tradePassEntity, isDispose,pageStart,pageEnd);
        long count= mapper.selectAllSpecialDataCount(tradePassEntity, isDispose);
        if (StringUtils.isNotEmpty(tradePassEntity.getSpecialtype())) {
            for (int i = 0; i < tradePassEntityList.size(); i++) {
                TradePassEntity tradePass = tradePassEntityList.get(i);
                String[] specialTypes = tradePass.getSpecialtype().split("\\|");
                Arrays.sort(specialTypes);
                if (Arrays.binarySearch(specialTypes, tradePassEntity.getSpecialtype()) > -1) {
                    tradePassDataList.add(tradePass);
                }
                if (i == (tradePassEntityList.size() - 1)) {
                    tradePassEntityList.clear();
                    tradePassEntityList.addAll(tradePassDataList);
                    tradePassDataList.clear();
                }

            }
        }
        //?????????????????????????????????
        if (!ConstUtil.JCB_DEPT_ID.equals(loginUser.getDeptId())) {
            if (deptLongIdList != null) {
                for (int i = 0; i < tradePassEntityList.size(); i++) {
                    TradePassEntity tradePass = tradePassEntityList.get(i);
                    for (String deptLongId : deptLongIdList) {
                        if (tradePass.getSectionidgroup() != null && tradePass.getSectionidgroup().contains(deptLongId)) {
                            tradePassDataList.add(tradePass);
                            break;
                        }
                    }
                    if (i == (tradePassEntityList.size() - 1)) {
                        tradePassEntityList.clear();
                        tradePassEntityList.addAll(tradePassDataList);
                        tradePassDataList.clear();
                    }
                }
            }
        }
        //??????????????????
        //PageInfo pageInfo = new PageInfo<>(tradePassEntityList);
        //????????????
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, tradePassEntityList, count);
    }

    @Override
    public ResultMsg<List<List<String>>> getSpecialTradePassByPassIdList(List<String> passIdList) {
        ResultMsg result = new ResultMsg();
        //????????????Excle?????????
        List<List<String>> data = new ArrayList<>();
        //??????passIdList?????????????????????
        if (passIdList.isEmpty()) {
            result.setSuccess(true);
            result.setData(data);
            return result;
        }
        //????????????????????????????????????Map
        List<SysEnumDetailsEntity> colorEnumList = enumDetailMapper.findByEnumId(39L);
        Map<String, String> colorEnum = new HashMap<>();
        for (SysEnumDetailsEntity sysEnumDetails : colorEnumList) {
            colorEnum.put(sysEnumDetails.getEnumValue(), sysEnumDetails.getEnumName());
        }
        //????????????????????????????????????Map
        List<SysEnumDetailsEntity> specialTypeEnumList = enumDetailMapper.findByEnumId(86L);
        Map<String, String> specialTypeEnum = new HashMap<>();
        for (SysEnumDetailsEntity sysEnumDetails : specialTypeEnumList) {
            specialTypeEnum.put(sysEnumDetails.getEnumValue(), sysEnumDetails.getEnumName());
        }
        //??????
        List<TradePassEntity> tradePassList = mapper.getDataByPassIdList(passIdList);
        for (TradePassEntity tradePass : tradePassList) {
            List<String> tradePassDataList = new ArrayList<>();
            tradePassDataList.add(tradePass.getEnvehicleNo());
            tradePassDataList.add(colorEnum.get(tradePass.getEnvehicleColor() == null ? "" : tradePass.getEnvehicleColor().toString()));
            tradePassDataList.add(tradePass.getExvehicleNo());
            tradePassDataList.add(colorEnum.get(tradePass.getExvehicleColor() == null ? "" : tradePass.getExvehicleColor().toString()));
            tradePassDataList.add(tradePass.getEnsectionid());
            tradePassDataList.add(tradePass.getExsectionid());
            tradePassDataList.add(tradePass.getEntime() == null ? "" : DateFormat.getFormatDate(tradePass.getEntime()));
            tradePassDataList.add(tradePass.getExtime() == null ? "" : DateFormat.getFormatDate(tradePass.getExtime()));
            tradePassDataList.add(tradePass.getMediatypename());
            tradePassDataList.add(tradePass.getMediano());
            tradePassDataList.add(tradePass.getObuid());
            //????????????????????????????????????
            String[] specialTypeStrs = tradePass.getSpecialtype().split("\\|");
            StringBuilder specialTypes = new StringBuilder();
            for (int i = 0; i < specialTypeStrs.length; i++) {
                specialTypes.append(specialTypeEnum.get(specialTypeStrs[i]));
                if (i < (specialTypeStrs.length - 1)) {
                    specialTypes.append("|");
                }
            }
            tradePassDataList.add(specialTypes.toString());
            tradePassDataList.add(tradePass.getEnvehicletypename());
            tradePassDataList.add(tradePass.getExvehicletypename());
            tradePassDataList.add(tradePass.getEnvehicleclassname());
            tradePassDataList.add(tradePass.getExvehicleclassname());
            tradePassDataList.add(tradePass.getEntolllaneid());
            tradePassDataList.add(tradePass.getExtolllaneid());
            tradePassDataList.add(tradePass.getEnprovinceid());
            tradePassDataList.add(tradePass.getExprovinceid());
            tradePassDataList.add(tradePass.getEntollstationid());
            tradePassDataList.add(tradePass.getExtollstationid());
            tradePassDataList.add(tradePass.getPassid());
            tradePassDataList.add(tradePass.getCdinserttime() == null ? "" : DateFormat.getFormatDate(tradePass.getCdinserttime()));
            data.add(tradePassDataList);
        }
        result.setSuccess(true);
        result.setData(data);
        result.setMessage("????????????");
        return result;
    }


    @Override
    public Integer selectCount(Map<String, Object> conditions) {
        // TODO Auto-generated method stub
        Integer count = mapper.selectCount(conditions);
        return count;
    }


    @Override
    public List<TradePassEntity> selecTradePassListExport(Map<String, Object> conditions) {
        // TODO Auto-generated method stub
        List<TradePassEntity> list = mapper.selecTradePassListExport(conditions);
        return list;
    }



    /**
     * wwx
     * @param passIds ??????ID
     * @return
     */
    @Override
    public Map cpcFitting(String passIds) {
        Map map=new HashMap();
        Double feeCount=0.00;
        Double payFeeCount=0.00;
        Integer tollIntervalIdlen=0;
        Double length=0.00;
        ArrayList list=new ArrayList();//?????????????????????????????????????????????
        List tollIntervalIdList=new ArrayList();
        List jcGantryList=new ArrayList();
            JSONObject jsonPassIdObject=new JSONObject();
            jsonPassIdObject.put("passId",passIds);
            String jsonStr=jsonPassIdObject.toJSONString();
            String str = sendPost("http://10.212.35.71/fee/tradepass/MTC_CHARGING_REQ_G12345678912345_20191024020102457@_@json", jsonStr);
            JSONObject jsonObject = JSONObject.parseObject(str);
            if(Integer.parseInt(jsonObject.get("code").toString())==0) {
                JSONArray data = jsonObject.getJSONArray("data");
                if(data.size()<=0){
                    map.put("list",jcGantryList);
                    map.put("feeCount",feeCount);
                    map.put("payFeeCount",payFeeCount);
                    map.put("tollIntervalIdlen",tollIntervalIdlen);
                    map.put("length",0);
                    map.put("size",list.size());
                    return map;
                }
                for (int i=0;i<data.size();i++) {
                    JCGantryFitting jcGantryFitting=new JCGantryFitting();
                    JSONObject o = (JSONObject) data.get(i);
                    String id = (String) o.get("id");
                    String name=mapper.getNameById(id.substring(0,19));//?????????????????????TOLLGRANTRY???????????????????????????????????????????????????????????????
                    Integer fee = (Integer) o.get("fee");
                    Integer discountFee = (Integer) o.get("discountFee");
                    Date enTime = new Date((Long) o.get("enTime"));
                    String etcCardId = (String) o.get("etcCardId");
                    String vehicleId = (String) o.get("vehicleId");
                    Integer vehicleType = (Integer) o.get("vehicleType");
                    Integer vehicleClass = (Integer) o.get("vehicleClass");
                    String obuId = (String) o.get("obuId");
                    Integer etcCardType = (Integer) o.get("etcCardType");
                    Date transTime = new Date((Long)o.get("transTime"));
                    String tollIntervalId = (String) o.get("tollIntervalId");
                    String[] strings=tollIntervalId.split("\\|");//??????????????????
                    for(String tollstr:strings){
                        tollIntervalIdList.add(tollstr);
                    }
                    tollIntervalIdlen+=strings.length;
                    Integer transFee = (Integer) o.get("transFee");
                    String passId = (String) o.get("passId");
                    String issuerId = (String) o.get("issuerId");
                    Integer payFee = (Integer) o.get("payFee");
                    Integer enWeight = (Integer) o.get("enWeight");
                    Integer enAxleCount = (Integer) o.get("enAxleCount");
                    String tollIntervalPayFee = (String) o.get("tollIntervalPayFee");
                    String tollIntervalDiscountFee = (String) o.get("tollIntervalDiscountFee");
                    String tollIntervalFee = (String) o.get("tollIntervalFee");
                    Integer plateColor = (Integer) o.get("plateColor");
                    String feeLogMsg = (String) o.get("feeLogMsg");
                    String sectionId = (String) o.get("sectionId");
                    String enTollStationName = (String) o.get("enTollStationName");
                    String tollIntervalName = (String) o.get("tollIntervalName");
                    String sectionName = (String) o.get("sectionName");
                    String tollProvinceId=o.getString("tollProvinceId");
                    list.add(tollProvinceId);
                    jcGantryFitting.setId(id);
                    jcGantryFitting.setName(name);
                    jcGantryFitting.setDiscountFee(discountFee);
                    jcGantryFitting.setEnAxleCount(enAxleCount);
                    jcGantryFitting.setEnTime(enTime);
                    jcGantryFitting.setEnTollStationName(enTollStationName);
                    jcGantryFitting.setEnWeight(enWeight);
                    jcGantryFitting.setEtcCardId(etcCardId);
                    jcGantryFitting.setFee(fee);
                    jcGantryFitting.setFeeLogMsg(feeLogMsg);
                    jcGantryFitting.setIssuerId(issuerId);
                    jcGantryFitting.setObuId(obuId);
                    jcGantryFitting.setPassId(passId);
                    jcGantryFitting.setPlateColor(plateColor);
                    jcGantryFitting.setSectionId(sectionId);
                    jcGantryFitting.setTransTime(transTime);
                    jcGantryFitting.setTollIntervalName(tollIntervalName);
                    jcGantryFitting.setSectionName(sectionName);
                    jcGantryFitting.setVehicleId(vehicleId);
                    jcGantryFitting.setVehicleType(vehicleType);
                    jcGantryFitting.setVehicleClass(vehicleClass);
                    jcGantryFitting.setEtcCardType(etcCardType);
                    jcGantryFitting.setTollIntervalId(tollIntervalId);
                    jcGantryFitting.setPayFee(payFee);
                    jcGantryFitting.setTransFee(transFee);
                    jcGantryFitting.setTollIntervalPayFee(tollIntervalPayFee);
                    jcGantryFitting.setTollIntervalDiscountFee(tollIntervalDiscountFee);
                    jcGantryFitting.setTollIntervalFee(tollIntervalFee);
                    feeCount+=fee;
                    payFeeCount+=payFee;
                    jcGantryList.add(jcGantryFitting);
                }
            }
        Double length1= mapper.getLnegthByTollIntervalIdList(tollIntervalIdList);//???????????????????????????passId?????????????????????
        List newList = (List) list.stream().distinct().collect(Collectors.toList());//???????????????????????????
        map.put("list",jcGantryList);
        map.put("feeCount",feeCount/100);
        map.put("payFeeCount",payFeeCount/100);
        map.put("tollIntervalIdlen",tollIntervalIdlen);
        map.put("length",length1/1000);
        Stream s=list.stream().distinct();
        map.put("size",newList.size());
        return  map;
    }

    @Override
    public List<String> getTollgrantryIds(String sectionid) {
        return mapper.getTollgrantryIds(sectionid);
    }

    //Post????????????
    public String sendPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            /** ?????????URL??????????????? **/
            URLConnection conn = realUrl.openConnection();
            /** ???????????????????????? **/
            conn.setConnectTimeout(100000);
            conn.setReadTimeout(100000);
            /** ??????????????????????????? **/
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("binfile-auth", "myTicket");
            /** ??????POST?????????????????????????????? **/
            conn.setDoOutput(true);
            conn.setDoInput(true);
            /** ??????URLConnection???????????????????????? **/
            out = new PrintWriter(conn.getOutputStream());
            /** ?????????????????? **/
            out.print(param);
            /** flush?????????????????? **/
            out.flush();
            /** ??????BufferedReader??????????????????URL????????? **/
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//???????????????????????????
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public String covnDate(String dateTime) {
        java.text.DateFormat df2 = null;
        Date date = null;
        try {
            java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.parse(dateTime);
            df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df2.format(date);
    }
    //?????????????????????16???????????????

    public static String str2HexStr(String str) {
        String str1 = "";
        try {
            byte[] b = str.getBytes("gb2312");
            int i = 0;
            int max = b.length;
            for (; i < max; i++) {
                str1 = str1 + Integer.toHexString(b[i] & 0xFF);
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("????????????ToHexString" + e.getMessage());
        }
        return str1;

    }


	@Override
	public List<Map<String, Object>> selectStation(Map<String, Object> condition) {
        return mapper.selectStationByCondition(condition);
	}
}
