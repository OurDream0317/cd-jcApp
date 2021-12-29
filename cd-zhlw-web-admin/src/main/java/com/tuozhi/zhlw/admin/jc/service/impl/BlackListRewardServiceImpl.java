package com.tuozhi.zhlw.admin.jc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tuozhi.zhlw.admin.jc.mapper.BlackListRewardMapper;
import com.tuozhi.zhlw.admin.jc.service.BlackListRewardService;

import lombok.Cleanup;

@Service
public class BlackListRewardServiceImpl  implements BlackListRewardService{
	
	@Resource
    private BlackListRewardMapper mapper;
	
	@Value("${fileDownPath}")
    private String fileDownPath; 
	
	@Override
	public List<Map<String, Object>> getBlackListRewardDataByCondition(Map<String, Object> condition) {
		return mapper.getBlackListRewardDataByCondition(condition);
	}
	@Override
	public Long getBlackListRewardTotalCountByCondition(Map<String, Object> condition) {
		return mapper.getBlackListRewardTotalCountByCondition(condition);
	}
	
	@Override
	public String exportBlackListRewardDataExcel(List<Map<String, Object>> blackListRewardData) {
        File file = null;
        try {
            String[] titles = {"稽查表单编号", "车牌号", "稽查表单创建时间", "查获站", "黑名单申请编号", "黑名单添加申请提交站编号",
            		"追缴金额（元）", "提取比例（%）", "奖励金额（元）"
                    , "备注详情"};
            String[] rows = {"AUDITID", "CARNUMBER", "AUDITCREATETIME", "HUNTSTATIONNAME", "BLACKLISTID", "BLACKLISTSTATIONNAME", 
            		"RECOVERFEE", "COMMISPROPOR", "REWARDFEE",
                    "REQUESTDESCRIPTION"};

            // 1. 创建工作簿
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 2. 创建工作表
            XSSFSheet sheet = workbook.createSheet("黑名单添加奖励申请明细");

            //生成列标题行
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < titles.length; i++) {
                //设置列宽
                sheet.setColumnWidth(i, 21 * 150);
                XSSFCell cell = row.createCell(i);
                XSSFRichTextString string = new XSSFRichTextString(titles[i]);
                XSSFFont xssfFont = workbook.createFont();
                xssfFont.setBold(true);
                string.applyFont(xssfFont);
                cell.setCellValue(string);
            }

            //填充列数据
            for (int i = 0; i < blackListRewardData.size(); i++) {
                XSSFRow row1 = sheet.createRow(i + 1);
                for (int j = 0; j < rows.length; j++) {
                    XSSFCell cell = row1.createCell(j);
                    cell.setCellValue(blackListRewardData.get(i).get(rows[j]) + "");
                }
            }
            //文件地址
            File folderFile = new File(fileDownPath + "/blackListRewardDataExcel/excel"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            if (!folderFile.exists()) //noinspection ResultOfMethodCallIgnored
                folderFile.mkdirs();
            String exportPath = "黑名单添加奖励申请明细.xlsx";
            file = new File(folderFile, exportPath);
            @Cleanup FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(file).getAbsolutePath();
    }
}
