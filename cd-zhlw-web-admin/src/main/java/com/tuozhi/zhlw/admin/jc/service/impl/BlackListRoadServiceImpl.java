package com.tuozhi.zhlw.admin.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.*;
import com.tuozhi.zhlw.admin.jc.entity.grayListEntity.JCGrayListAttachment;
import com.tuozhi.zhlw.admin.jc.mapper.BlackListRoadMapper;
import com.tuozhi.zhlw.admin.jc.service.BlackListRoadService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName BlackListRoadServiceImpl
 * @Descriotion TODO 稽核下发黑名单车道业务实现
 * @Author liyuyan
 * @Date 2019/11/06 19:40
 * @Version 1.0
 */
@Service
@Slf4j
public class BlackListRoadServiceImpl implements BlackListRoadService {

    @Resource
    BlackListRoadMapper blackListRoadMapper;


    @Value("${fileDownPath}")
    private String fileDownPath;

    /**
     * 根据车牌号、车牌颜色查询
     * @param vehicleLicense
     * @param vehicleLicenseColor
     * @return
     */
    @Override
    public ResultMsg findByVehicleId(String vehicleLicense,Long vehicleLicenseColor){
        ResultMsg result = new ResultMsg();
        //查询判断此车牌是否已存在
        JcRoadBlackListEntity blackListData = blackListRoadMapper.findByVehicleId(vehicleLicense,vehicleLicenseColor);
        if(blackListData == null){
            result.setSuccess(false);
            return result;
        }
        result.setSuccess(true);
        result.setData(blackListData);
        return result;
    }

    /**
     * 修改已存在的车辆，并新增一条历史记录
     * @param roadBlackListEntity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg updateRoadBlackList(JcRoadBlackListEntity roadBlackListEntity){
        ResultMsg result = new ResultMsg();
        String plateId = roadBlackListEntity.getVehicleLicense();
        Long plateColor = roadBlackListEntity.getVehicleLicenseColor();
        JcRoadBlackListEntity blackListData = blackListRoadMapper.findByVehicleId(plateId,plateColor);
        BigDecimal evadeToll = BigDecimal.valueOf(roadBlackListEntity.getEvadeToll()).multiply(new BigDecimal(100));
        roadBlackListEntity.setListId(blackListData.getListId());
        roadBlackListEntity.setStopStatus(roadBlackListEntity.getStopStatus());
        roadBlackListEntity.setTimeRange("0"); //拦截时间段，默认为0
        roadBlackListEntity.setRoad("0"); //生效路段 默认为0代表拉黑全路段
        roadBlackListEntity.setListVersion("0"); //版本号
        roadBlackListEntity.setOperateFlag(1L); //当前名单状态（1：添加 2：修改 3：删除）
        roadBlackListEntity.setVehicleLicense(roadBlackListEntity.getVehicleLicense());
        roadBlackListEntity.setVehicleLicenseColor(roadBlackListEntity.getVehicleLicenseColor());
        roadBlackListEntity.setCpuId(roadBlackListEntity.getCpuId());
        roadBlackListEntity.setObuId(roadBlackListEntity.getObuId());
        roadBlackListEntity.setEvadeToll(evadeToll.doubleValue()); //逃费金额
        roadBlackListEntity.setSumTotal(blackListData.getStopStatus() == 0 ? 0 : 1); //逃费条数
        roadBlackListEntity.setHtmlStr(roadBlackListEntity.getHtmlStr());
        roadBlackListEntity.setRequestId(roadBlackListEntity.getRequestId());
        roadBlackListEntity.setTransFertag(0L); //传输标志位，默认未传输
        roadBlackListEntity.setGrayRequestId(roadBlackListEntity.getGrayRequestId());
        roadBlackListEntity.setMessageIdGrey(roadBlackListEntity.getMessageIdGrey());
        roadBlackListEntity.setEvasionType(roadBlackListEntity.getEvasionType());
        roadBlackListEntity.setEvasionKind(roadBlackListEntity.getEvasionKind());
        roadBlackListEntity.setEvadeTime(roadBlackListEntity.getEvadeTime());
        roadBlackListEntity.setEvadeSite(roadBlackListEntity.getEvadeSite());
        roadBlackListEntity.setLostCard(roadBlackListEntity.getLostCard());
        roadBlackListEntity.setPhoneNumber(roadBlackListEntity.getPhoneNumber());
        roadBlackListEntity.setCaseDescription(roadBlackListEntity.getCaseDescription());
        roadBlackListEntity.setCreateTime(new Date());
        roadBlackListEntity.setCdInsertTime(new Date());
        roadBlackListEntity.setStopStatusType(roadBlackListEntity.getStopStatusType());
        //如果新增的该灰名单车辆已存在，更新该车辆信息
        if(blackListData.getStopStatus() == 0){
            JcRoadBlackListEntity jcRoadBlackListEntity = new JcRoadBlackListEntity();
            jcRoadBlackListEntity.setEvasionType(blackListData.getEvasionType());
            jcRoadBlackListEntity.setEvasionKind(blackListData.getEvasionKind());
            jcRoadBlackListEntity.setEvadeToll(blackListData.getEvadeToll());
            jcRoadBlackListEntity.setEvadeTime(blackListData.getEvadeTime());
            jcRoadBlackListEntity.setSumTotal(0); //逃费条数置0
            jcRoadBlackListEntity.setEvadeSite(blackListData.getEvadeSite());
            jcRoadBlackListEntity.setLostCard(blackListData.getLostCard());
            jcRoadBlackListEntity.setCpuId(blackListData.getCpuId());
            jcRoadBlackListEntity.setObuId(blackListData.getObuId());
            jcRoadBlackListEntity.setTransFertag(0L);
            jcRoadBlackListEntity.setCreateTime(new Date()); //更新时间
            jcRoadBlackListEntity.setOperateFlag(2L); //更新标志位为2修改状态
            jcRoadBlackListEntity.setVehicleLicense(blackListData.getVehicleLicense());
            jcRoadBlackListEntity.setVehicleLicenseColor(blackListData.getVehicleLicenseColor());
            int addCount = blackListRoadMapper.updateGreyList(jcRoadBlackListEntity);
            if(addCount < 1){
                result.setSuccess(false);
                result.setMessage("保存失败！");
                return result;
            }
        }else { //如果新增的该黑名单车辆已存在，更新如下内容
            JcRoadBlackListEntity jcRoadBlackListEntity = new JcRoadBlackListEntity();
            BigDecimal htEvadeToll = new BigDecimal(Double.toString(blackListData.getEvadeToll()));
            Double TotalevadeToll = evadeToll.add(htEvadeToll).doubleValue(); //累加计算逃费金额
            jcRoadBlackListEntity.setSumTotal(roadBlackListEntity.getSumTotal() + blackListData.getSumTotal());//累加逃费条数
            jcRoadBlackListEntity.setEvadeToll(TotalevadeToll);
            jcRoadBlackListEntity.setTransFertag(0L); //重置传输标志位为未传输状态
            jcRoadBlackListEntity.setVehicleLicense(blackListData.getVehicleLicense());
            jcRoadBlackListEntity.setVehicleLicenseColor(blackListData.getVehicleLicenseColor());
            jcRoadBlackListEntity.setCreateTime(new Date()); //更新时间
            jcRoadBlackListEntity.setOperateFlag(2L); //更新标志位为2修改状态
            int addCount = blackListRoadMapper.updateBlackList(jcRoadBlackListEntity);
            if(addCount < 1){
                result.setSuccess(false);
                result.setMessage("保存失败！");
                return result;
            }
        }
        int addCount = blackListRoadMapper.addBlackListHistory(roadBlackListEntity);
        if(addCount < 1){
            throw new RuntimeException("新增失败，请重试！");
        }
          result.setSuccess(true);
        result.setMessage("保存成功！");
        return result;
    }

    @Override
    public ResultMsg newUpdateRoadBlackList(JcRoadBlackListEntity roadBlackListEntity) {
        ResultMsg result = new ResultMsg();
        String plateId = roadBlackListEntity.getVehicleLicense();
        Long plateColor = roadBlackListEntity.getVehicleLicenseColor();
        JcRoadBlackListEntity blackListData = blackListRoadMapper.findByVehicleId(plateId,plateColor);
        //更新关注名单
        if(roadBlackListEntity.getStopStatus() == 0){
            roadBlackListEntity.setSumTotal(0); //逃费条数置0
            roadBlackListEntity.setCreateTime(new Date()); //更新时间
            roadBlackListEntity.setOperateFlag(2L); //更新标志位为2修改状态
            roadBlackListEntity.setListVersion("0");//版本号设置为0
            roadBlackListEntity.setTransFertag(0L);
            roadBlackListEntity.setEvadeToll(roadBlackListEntity.getEvadeToll()*100);
            int addCount = blackListRoadMapper.newUpdateGreyList(roadBlackListEntity);
            if(addCount < 1){
                result.setSuccess(false);
                result.setMessage("保存失败！");
                return result;
            }
        }else { //更新追缴黑名单
            roadBlackListEntity.setCreateTime(new Date()); //更新时间
            roadBlackListEntity.setOperateFlag(2L); //更新标志位为2修改状态
            roadBlackListEntity.setTransFertag(0L);
            roadBlackListEntity.setListVersion("0");//版本号设置为0
            roadBlackListEntity.setEvadeToll(roadBlackListEntity.getEvadeToll()*100);
            int addCount = blackListRoadMapper.updateBlackList(roadBlackListEntity);
            if(addCount < 1){
                result.setSuccess(false);
                result.setMessage("保存失败！");
                return result;
            }
        }
        roadBlackListEntity.setListId(blackListData.getListId());
        roadBlackListEntity.setRoad(blackListData.getRoad());
        roadBlackListEntity.setTransFertag(blackListData.getTransFertag());
        roadBlackListEntity.setGrayRequestId(blackListData.getGrayRequestId());
        roadBlackListEntity.setSumTotal(blackListData.getSumTotal());
        roadBlackListEntity.setEvadeToll(roadBlackListEntity.getEvadeToll());
        int addCount = blackListRoadMapper.addBlackListHistory(roadBlackListEntity);
        if(addCount < 1){
            throw new RuntimeException("新增失败，请重试！");
        }
        result.setSuccess(true);
        result.setMessage("保存成功！");
        return result;
    }

    /**
     * 黑名单车道添加
     * @param roadBlackListEntity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg saveRoadBlackList(JcRoadBlackListEntity roadBlackListEntity){
        ResultMsg result = new ResultMsg();
        String listId = blackListRoadMapper.findListId();
        roadBlackListEntity.setListId(listId); //uuid
        BigDecimal evadeToll = BigDecimal.valueOf(roadBlackListEntity.getEvadeToll()).multiply(new BigDecimal(100));
        roadBlackListEntity.setStopStatus(roadBlackListEntity.getStopStatus());
        roadBlackListEntity.setTimeRange("0"); //拦截时间段，默认为0
        roadBlackListEntity.setRoad("0"); //生效路段 默认为0代表拉黑全路段
        roadBlackListEntity.setListVersion("0"); //版本号
        roadBlackListEntity.setOperateFlag(1L); //当前名单状态（1：添加 2：修改 3：删除）
        roadBlackListEntity.setVehicleLicense(roadBlackListEntity.getVehicleLicense());
        roadBlackListEntity.setVehicleLicenseColor(roadBlackListEntity.getVehicleLicenseColor());
        roadBlackListEntity.setCpuId(roadBlackListEntity.getCpuId());
        roadBlackListEntity.setObuId(roadBlackListEntity.getObuId());
        roadBlackListEntity.setEvadeToll(evadeToll.doubleValue()); //逃费金额
        roadBlackListEntity.setSumTotal(1); //逃费条数
        roadBlackListEntity.setHtmlStr(roadBlackListEntity.getHtmlStr());
        roadBlackListEntity.setRequestId(roadBlackListEntity.getRequestId());
        roadBlackListEntity.setTransFertag(0L); //传输标志位，默认未传输
        roadBlackListEntity.setGrayRequestId(roadBlackListEntity.getGrayRequestId());
        roadBlackListEntity.setMessageIdGrey(roadBlackListEntity.getMessageIdGrey());
        roadBlackListEntity.setEvasionType(roadBlackListEntity.getEvasionType());
        roadBlackListEntity.setEvasionKind(roadBlackListEntity.getEvasionKind());
        roadBlackListEntity.setEvadeTime(roadBlackListEntity.getEvadeTime());
        roadBlackListEntity.setEvadeSite(roadBlackListEntity.getEvadeSite());
        roadBlackListEntity.setLostCard(roadBlackListEntity.getLostCard());
        roadBlackListEntity.setPhoneNumber(roadBlackListEntity.getPhoneNumber());
        roadBlackListEntity.setCaseDescription(roadBlackListEntity.getCaseDescription());
        roadBlackListEntity.setCreateTime(new Date());
        roadBlackListEntity.setCdInsertTime(new Date()); //数据入库时间
        roadBlackListEntity.setStopStatusType(roadBlackListEntity.getStopStatusType());
        int addCount = blackListRoadMapper.addRoadBlackList(roadBlackListEntity);
        if(addCount < 1){
            result.setSuccess(false);
            result.setMessage("保存失败");
            return result;
        }
        int addCount1 = blackListRoadMapper.addBlackListHistory(roadBlackListEntity);
        if(addCount1 < 1){
            throw new RuntimeException("添加失败，请重试！");
        }
        result.setSuccess(true);
        result.setMessage("保存成功");
        return  result;
    }

    /**
     * 1、查询所有黑名单信息 2、根据条件查询黑名单列表信息
     * @return
     */
    @Override
    public ResultExtGrid findBlackListAll(JcRoadBlackListEntity jcRoadBlackListEntity, QueryParams queryParams){
        if(StringUtils.isEmpty(jcRoadBlackListEntity.getVehicleLicense())){
            jcRoadBlackListEntity.setVehicleLicense(null);
        }
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<JcRoadBlackListEntity> roadBlackList = blackListRoadMapper.findBlackListAll(jcRoadBlackListEntity);
        PageInfo pageInfo = new PageInfo<>(roadBlackList);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 根据sid查询黑名单历史记录信息
     * @param sid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg findHistoryAll(Long sid,String vehicleLicense,Long vehicleLicenseColor,Double evadeToll,String htmlStr,Long requestId){
        ResultMsg result = new ResultMsg();
        JcRoadBlackListEntity jcRoadBlackListEntity = new JcRoadBlackListEntity();
        jcRoadBlackListEntity.setSid(sid);
        if(evadeToll!=null) {
            BigDecimal bg1 = new BigDecimal(evadeToll * 100);
            evadeToll = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        jcRoadBlackListEntity.setEvadeToll(evadeToll);
        jcRoadBlackListEntity.setVehicleLicense(vehicleLicense);
        jcRoadBlackListEntity.setVehicleLicenseColor(vehicleLicenseColor);
        //根据sid查询车道黑名单信息
        List<JcRoadBlackListEntity> roadBlackList = blackListRoadMapper.findBlackListAll(jcRoadBlackListEntity);
        if(roadBlackList.size()>0) {
            jcRoadBlackListEntity = roadBlackList.get(0);
            if (jcRoadBlackListEntity.getStopStatus() == 0&&sid!=null) {
                //代表不拦截为灰名单，物理删除
                blackListRoadMapper.delBlackList(jcRoadBlackListEntity.getListId());
                //同步更新历史表中黑名单状态为移除状态
                jcRoadBlackListEntity.setOperateFlag(3L);
            }else if (jcRoadBlackListEntity.getStopStatus() == 0&&sid==null) {//当操作为撤销黑名单操作时，车道本地黑灰名单状态为删除状态，不做任何操作
                result.setSuccess(true);
                return result;
            }
            else if (jcRoadBlackListEntity.getStopStatus() == 1 || jcRoadBlackListEntity.getStopStatus() == 2 || jcRoadBlackListEntity.getStopStatus() == 3) {
                //代表为黑名单，转为灰名单并更新为修改状态
                jcRoadBlackListEntity.setStopStatus(0L);
                jcRoadBlackListEntity.setEvadeToll(0d); //黑名单转白，将逃费金额置为0
                jcRoadBlackListEntity.setSumTotal(0); //逃费条数置0
                jcRoadBlackListEntity.setOperateFlag(2L); //更新标志位为修改状态
                jcRoadBlackListEntity.setStopStatusType(0); //更新拦截类型为0不拦截
                blackListRoadMapper.uptIsGreyList(jcRoadBlackListEntity.getListId(), htmlStr,requestId);
            }
            jcRoadBlackListEntity.setCreateTime(new Date());
            jcRoadBlackListEntity.setHtmlStr(htmlStr);
            jcRoadBlackListEntity.setCxRequestId(requestId);
            int addCount = blackListRoadMapper.addBlackListHistory(jcRoadBlackListEntity);
            if (addCount < 1) {
                result.setSuccess(false);
                log.info("车道本地黑灰名单转灰(删除)过程中"+vehicleLicense+"的车辆插入历史表中失败");
                throw new RuntimeException("添加失败");
            }
            result.setSuccess(true);
        }else{
            log.info("车道本地黑灰名单转灰过程中，不存在"+vehicleLicense+"的车牌（车牌，车牌颜色，逃费金额）");
            result.setSuccess(false);
            if(sid==null) {
                result.setMessage("本地黑灰名单转灰状态名单不存在");
            }
        }
        return result;
    }

    /**
     * 查询车道黑名单历史，根据uuid
     * @param listId
     * @return
     */
    @Override
    public ResultExtGrid findHistoryByListId(JcRoadBlackListEntity jcRoadBlackListEntity, QueryParams queryParams){
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<JcRoadBlackListHistoryEntity> roadBlackListHistoryData = blackListRoadMapper.findBlackListHistroyAll(jcRoadBlackListEntity);
        PageInfo pageInfo = new PageInfo<>(roadBlackListHistoryData);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 根据条件查询的黑灰名单信息导出
     * @param selectMap
     * @return
     */
    @Override
    public String exportLocalBlackExcel(Map<String, Object> selectMap){
        List<Map<String, Object>> BlackGreyList = blackListRoadMapper.selectBlackGreyAll(selectMap);
        return exportExcel(BlackGreyList);
    }

    @Override
    public void deleteLocalGreyList(long[] sids) {
        blackListRoadMapper.deleteLocalGreyList(sids);
    }

    private String exportExcel(List<Map<String, Object>> data) {
        File file = null;
        try {
            String[] titles = {"车牌号码", "车辆颜色", "生成名单类型", "是否拦截", "拦截类型", "ETC卡号", "OBU卡号","车辆特征","车型","轴型","车座数", "创建时间", "逃费类型", "逃费种类", "逃费金额（元）", "逃费时间",
                    "逃费地点","逃费通行条数", "当前名单状态", "是否赔卡", "联系电话", "生效路段", "版本号", "描述"};
            String[] rows = {"VEHICLELICENSE", "VEHICLELICENSECOLOR", "NAMETYPE", "STOPSTATUS", "STOPSTATUSTYPE", "CPUID", "OBUID","CARFEATURE","CARTYPE","AXLETYPE","SEAT_NUM", "CD_INSERTTIME", "EVASIONTYPE", "EVASIONKIND", "EVADETOLL",
                    "EVADETIME", "EVADESITE","SUMTOTAL", "OPERATEFLAG", "LOSTCARD", "PHONENUMBER", "ROAD", "LISTVERSION", "CASEDESCRIPTION"};

            // 1. 创建工作簿
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 2. 创建工作表
            XSSFSheet sheet = workbook.createSheet("车道本地黑灰名单");

            //生成列标题行
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < titles.length; i++) {
                //设置列宽
                sheet.setColumnWidth(i, 21 * 256);
                XSSFCell cell = row.createCell(i);
                XSSFRichTextString string = new XSSFRichTextString(titles[i]);
                XSSFFont xssfFont = workbook.createFont();
                xssfFont.setBold(true);
                string.applyFont(xssfFont);
                cell.setCellValue(string);
            }

            //填充列数据
            for (int i = 0; i < data.size(); i++) {
                XSSFRow row1 = sheet.createRow(i + 1);
                for (int j = 0; j < rows.length; j++) {
                    XSSFCell cell = row1.createCell(j);
                    cell.setCellValue(data.get(i).get(rows[j])==null?"":data.get(i).get(rows[j]).toString());
                }
            }
            //文件地址
            File folderFile = new File(fileDownPath + "/localBlackListExcel");
            if (!folderFile.exists()) //noinspection ResultOfMethodCallIgnored
                folderFile.mkdirs();
            String exportPath = "车道本地黑灰名单" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".xlsx";

            file = new File(folderFile, exportPath);
            @Cleanup FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(file).getAbsolutePath();
    }

    /**
     * 导入Excel
     * @param fileName
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg readBlackListExcelInfo(File fileName){
        ResultMsg result = new ResultMsg();
        List<JcRoadBlackListEntity> list = new ArrayList<JcRoadBlackListEntity>();

        FileInputStream fis = null;
        Workbook workbook = null;

        String filePath = fileName.toString();
        String extString = filePath.substring(filePath.lastIndexOf("."));
        try {
            fis = new FileInputStream(fileName);
            if(".xls".equals(extString)) {
                workbook = new HSSFWorkbook(fis);// 得到工作簿// 2003版本的excel，用.xls结尾
            }else if(".xlsx".equals(extString)) {
                workbook = new XSSFWorkbook(fis);// 得到工作簿// 2007版本的excel，用.xlsx结尾
            }else {
                workbook = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 得到一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();

        //声明要获得的属性
        String vehicleLicense; //车牌号
        Integer vehicleLicenseColor; //车牌颜色
        String cpuId; //cpu卡号
        String obuId; //obu卡号
        Integer operateFlag; //当前名单状态
        Integer sumTotal; //逃费条数
        BigDecimal evadeToll; //逃费金额
        JcRoadBlackListEntity jcRoadBlackListEntity = null;
        // 遍历获得所有数据
        for (int i = 1; i <= totalRowNum; i++) {
            jcRoadBlackListEntity = new JcRoadBlackListEntity();
            String listId = blackListRoadMapper.findListId();
            jcRoadBlackListEntity.setListId(listId); //UUID
            jcRoadBlackListEntity.setCdInsertTime(new Date());//创建时间
            jcRoadBlackListEntity.setCreateTime(new Date());//更新时间
            jcRoadBlackListEntity.setStopStatus(3L); //是否拦截（默认全部拦截）
            jcRoadBlackListEntity.setStopStatusType(3); //拦截类型（默认全部拦截）
            // 获得第i行对象
            Row row = sheet.getRow(i);
            // 获得第i行第0列的int类型对象
            Cell cell = null;
            cell = row.getCell(0);
            if(cell!=null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                vehicleLicense = cell.getStringCellValue().trim();
                jcRoadBlackListEntity.setVehicleLicense(vehicleLicense);
            }else{
                jcRoadBlackListEntity.setVehicleLicense(null);
            }
            cell = row.getCell(1);
            if(cell!=null){
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                vehicleLicenseColor = (int)cell.getNumericCellValue();
                jcRoadBlackListEntity.setVehicleLicenseColor(vehicleLicenseColor.longValue());
            }else{
                jcRoadBlackListEntity.setVehicleLicenseColor(null);
            }


            cell = row.getCell(2);
            if(cell!=null){
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cpuId = cell.getStringCellValue().trim();
            jcRoadBlackListEntity.setCpuId(cpuId);
            }else{
                jcRoadBlackListEntity.setCpuId(null);
            }
            if(cell!=null) {
                cell = row.getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                obuId = cell.getStringCellValue().trim();
                jcRoadBlackListEntity.setObuId(obuId);
            }else{
                jcRoadBlackListEntity.setObuId(null);
            }
            cell = row.getCell(4);
            if(cell!=null){
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                operateFlag = (int)cell.getNumericCellValue();
                jcRoadBlackListEntity.setOperateFlag(operateFlag.longValue());
            }else{
                jcRoadBlackListEntity.setOperateFlag(null);
            }
            cell = row.getCell(5);

            if(cell!=null){
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                sumTotal = (int)cell.getNumericCellValue();
                jcRoadBlackListEntity.setSumTotal(sumTotal);
            }else{
                jcRoadBlackListEntity.setSumTotal(null);
            }


            cell = row.getCell(6);

            if(cell!=null){
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                evadeToll = BigDecimal.valueOf(cell.getNumericCellValue()).multiply(new BigDecimal(100));
                jcRoadBlackListEntity.setEvadeToll(evadeToll.doubleValue());
            }else{
                result.setSuccess(false);
                result.setMessage("导入失败,逃费金额不允许为空，请重试！");
                return result;
            }
            int addCount = blackListRoadMapper.addRoadBlackList(jcRoadBlackListEntity);
            if(addCount < 1){
                result.setSuccess(false);
                result.setMessage("导入失败，请重试！");
                return result;
            }
            int addCount1 = blackListRoadMapper.addBlackListHistory(jcRoadBlackListEntity);
            if(addCount1 < 1){
                throw new RuntimeException("导入失败，请重试！");
            }
            result.setSuccess(true);
            result.setMessage("文件导入成功！");
        }
        return result;
    }



    /**
     * 查看黑名单证据文件信息
     * @param requestId
     * @param queryParams
     * @return
     */
    @Override
    public ResultExtGrid getAttachmentById (Long requestId, QueryParams queryParams){
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<BlackListAttachment> blackAttachmentList = blackListRoadMapper.getDataByRequestId(requestId);
        PageInfo pageInfo = new PageInfo<>(blackAttachmentList);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 查看黑名单证据文件信息
     * @param grayRequestId
     * @param queryParams
     * @return
     */
    @Override
    public ResultExtGrid getGreyAttachmentById (Long grayRequestId, QueryParams queryParams){
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<JCGrayListAttachment> greyAttachmentList = blackListRoadMapper.getGreyAttachmentById(grayRequestId);
        PageInfo pageInfo = new PageInfo<>(greyAttachmentList);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public JcRoadBlackListTempEntity getLocalBlackListQueryDetails(Long sid) {
        JcRoadBlackListTempEntity localBlackListQueryDetailsBySid = blackListRoadMapper.getLocalBlackListQueryDetailsBySid(sid);
        return  localBlackListQueryDetailsBySid;
    }

    @Override
    public Integer addJCBlacklistPayment(JCBlacklistPayment jcBlacklistPayment,String listId) {
        Integer integer = blackListRoadMapper.addJCBlacklistPayment(jcBlacklistPayment);
        return integer;
    }

    @Override
    public ResultExtGrid getJCBlacklistPayment(QueryParams queryParams,String vehicle,Integer vehicleColor){
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<JCBlacklistPayment> jcBlacklistPaymentList = blackListRoadMapper.getJCBlacklistPayment(vehicle, vehicleColor);
        PageInfo pageInfo = new PageInfo<>(jcBlacklistPaymentList);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }
}
