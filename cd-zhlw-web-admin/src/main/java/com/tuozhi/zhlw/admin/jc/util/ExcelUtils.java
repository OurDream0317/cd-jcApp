package com.tuozhi.zhlw.admin.jc.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.tuozhi.zhlw.common.bean.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;

/**
 * @ClassName ExcelUtils
 * @Descriotion TODO Excle工具类型
 * @Author fujiankang
 * @Date 2019/12/15 18:50
 * @Version 1.0
 */
@Slf4j
public class ExcelUtils {
    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel
    //默认单元格内容为数字时格式
    private static DecimalFormat df = new DecimalFormat("0");
    // 默认单元格格式化日期字符串
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 格式化数字
    private static DecimalFormat nf = new DecimalFormat("0.00");

    public static void exportExcle(List<List<String>> data, String[] headTextNames, String fileName, HttpServletResponse response) throws IOException {
        response.reset(); //清除buffer缓存
        // 指定下载的文件名，浏览器都会使用本地编码，即GBK，浏览器收到这个文件名后，用ISO-8859-1来解码，然后用GBK来显示
        // 所以我们用GBK解码，ISO-8859-1来编码，在浏览器那边会反过来执行。
        fileName = fileName + excel2007U;
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //生成Excle文件
        XSSFWorkbook workbook = createExcelFile(data, headTextNames, fileName);
        OutputStream output = response.getOutputStream();
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
        bufferedOutPut.flush();
        workbook.write(bufferedOutPut);
        bufferedOutPut.close();
    }

    /**
     * 设置导出的Excle内容
     *
     * @param data
     * @param headTextNames
     * @return
     * @throws IllegalArgumentException
     */
    public static XSSFWorkbook createExcelFile(List<List<String>> data, String[] headTextNames, String sheetName) throws IllegalArgumentException {
        // 创建新的Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
        XSSFSheet sheet = workbook.createSheet(sheetName);
        // 以下为excel的标题与内容的创建，下面会具体分析;
        createTableHeader(sheet, headTextNames); //创建标题（头）
        createTableRows(sheet, headTextNames, data); //创建内容
        return workbook;
    }

    /**
     * 根据ExcelMapping 生成列头（多行列头）
     *
     * @param sheet
     * @param headTextNames
     */
    public static final void createTableHeader(XSSFSheet sheet, String[] headTextNames) {
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headTextNames.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headTextNames[i]);// 设置内容
        }
    }

    public static void createTableRows(XSSFSheet sheet, String[] headTextNames, List<List<String>> data) throws IllegalArgumentException {
        for (int i = 0; i < data.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            for (int j = 0; j < data.get(i).size(); j++) {
                String dataValue = data.get(i).get(j);
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(dataValue);
            }
        }
    }

    public static ArrayList<ArrayList<Object>> readExcel(File file) {

        if (file == null) {

            return null;

        }

        if (file.getName().endsWith("xlsx")) {

            //处理ecxel2007

            return readExcel2007(file);

        } else {

            //处理ecxel2003

            return readExcel2003(file);

        }

    }

    /*

     * @return 将返回结果存储在ArrayList内，存储结构与二位数组类似

     * lists.get(0).get(0)表示过去Excel中0行0列单元格

     */

    public static ArrayList<ArrayList<Object>> readExcel2003(File file) {

        try {

            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();

            ArrayList<Object> colList;

            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));

            HSSFSheet sheet = wb.getSheetAt(0);

            HSSFRow row;

            HSSFCell cell;

            Object value;

            for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {

                row = sheet.getRow(i);

                colList = new ArrayList<Object>();

                if (row == null) {

                    //当读取行为空时

                    if (i != sheet.getPhysicalNumberOfRows()) {//判断是否是最后一行

                        rowList.add(colList);

                    }

                    continue;

                } else {

                    rowCount++;

                }

                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {

                    cell = row.getCell(j);

                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {

                        //当该单元格为空

                        if (j != row.getLastCellNum()) {//判断是否是该行中最后一个单元格

                            colList.add("");

                        }

                        continue;

                    }

                    switch (cell.getCellType()) {

                        case XSSFCell.CELL_TYPE_STRING:

                            System.out.println(i + "行" + j + " 列 is String type");

                            value = cell.getStringCellValue();

                            break;

                        case XSSFCell.CELL_TYPE_NUMERIC:

                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {

                                value = df.format(cell.getNumericCellValue());

                            } else if ("General".equals(cell.getCellStyle()

                                    .getDataFormatString())) {

                                value = nf.format(cell.getNumericCellValue());

                            } else {

                                value = sdf.format(HSSFDateUtil.getJavaDate(cell

                                        .getNumericCellValue()));

                            }

                            System.out.println(i + "行" + j

                                    + " 列 is Number type ; DateFormt:"

                                    + value.toString());

                            break;

                        case XSSFCell.CELL_TYPE_BOOLEAN:

                            System.out.println(i + "行" + j + " 列 is Boolean type");

                            value = Boolean.valueOf(cell.getBooleanCellValue());

                            break;

                        case XSSFCell.CELL_TYPE_BLANK:

                            System.out.println(i + "行" + j + " 列 is Blank type");

                            value = "";

                            break;

                        default:

                            System.out.println(i + "行" + j + " 列 is default type");

                            value = cell.toString();

                    }// end switch

                    colList.add(value);

                }//end for j

                rowList.add(colList);

            }//end for i


            return rowList;

        } catch (Exception e) {

            return null;

        }

    }


    public static ArrayList<ArrayList<Object>> readExcel2007(File file) {

        try {

            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();

            ArrayList<Object> colList;

            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));

            XSSFSheet sheet = wb.getSheetAt(0);

            XSSFRow row;

            XSSFCell cell;

            Object value;

            for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {

                row = sheet.getRow(i);

                colList = new ArrayList<Object>();

                if (row == null) {

                    //当读取行为空时

                    if (i != sheet.getPhysicalNumberOfRows()) {//判断是否是最后一行

                        rowList.add(colList);

                    }

                    continue;

                } else {

                    rowCount++;

                }

                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {

                    cell = row.getCell(j);

                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {

                        //当该单元格为空

                        if (j != row.getLastCellNum()) {//判断是否是该行中最后一个单元格

                            colList.add("");

                        }

                        continue;

                    }


                    colList.add(cell.toString());

                }//end for j

                rowList.add(colList);

            }//end for i


            return rowList;

        } catch (Exception e) {

            System.out.println("exception");

            return null;

        }

    }

    //wwx
    public static void excelUtil(HttpServletResponse response, String[] titles, List<Map<String, Object>> list, String[] rows, String name) throws Exception {
        // 1. 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 2. 创建工作表
        XSSFSheet sheet = workbook.createSheet("数据");

        //生成列标题行
        XSSFRow row = sheet.createRow(0);
        Font cellFont = workbook.createFont();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellFont.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        for (int i = 0; i < titles.length; i++) {
            //设置列宽
            sheet.setColumnWidth(i, 21 * 256);
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString string = new XSSFRichTextString(titles[i]);
            XSSFFont xssfFont = workbook.createFont();
            xssfFont.setBold(true);
            string.applyFont(xssfFont);
            cell.setCellValue(string);
            cell.setCellStyle(cellStyle);
        }

        //填充列数据
        for (int i = 0; i < list.size(); i++) {
            XSSFRow row1 = sheet.createRow(i + 1);
            for (int j = 0; j < rows.length; j++) {
                XSSFCell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(rows[j]) == null ? "" : list.get(i).get(rows[j]).toString());
            }
        }
        String fileName = name + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".xlsx";
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream output = response.getOutputStream();
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
        bufferedOutPut.flush();
        workbook.write(bufferedOutPut);
        bufferedOutPut.close();
    }
}
