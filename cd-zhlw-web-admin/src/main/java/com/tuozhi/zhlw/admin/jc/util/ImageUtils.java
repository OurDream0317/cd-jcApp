package com.tuozhi.zhlw.admin.jc.util;

import com.alibaba.fastjson.JSONObject;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.utils.StringUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @ClassName ImageUtils
 * @Descriotion TODO 图片工具类
 * @Author fujiankang
 * @Date 2019/12/9 16:51
 * @Version 1.0
 */
public class ImageUtils {
    /**
     * 获取 门架图片 根据门架交易ID和车牌识别图像编号
     *
     * @param gantryId 门架交易ID
     * @param picId    车牌识别图像编号
     * @param url      访问的url
     * @return
     * @throws Exception
     */
    public static ResultMsg getGantryBase64Imag(String gantryId, String picId, String url) throws Exception {
        ResultMsg result = new ResultMsg();
        URL restURL = new URL(url);
        /*
         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
         */
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();// 设置是否从httpUrlConnection读入，默认情况下是true;
        conn.setDoInput(true);
        // 设置是否向httpUrlConnection输出
        conn.setDoOutput(true);
        // Post 请求不能使用缓存
        conn.setUseCaches(false);
        // 设定请求的方法，默认是GET
        conn.setRequestMethod("POST");
        // 设置字符编码连接参数
        conn.setRequestProperty("Connection", "Keep-Alive");
        // 设置字符编码
        conn.setRequestProperty("Charset", "UTF-8");
        // 设置请求内容类型
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // 设置DataOutputStream
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        //======传递参数======
        String param = "";
        if(StringUtils.isNotEmpty(gantryId)){
            param += "gantryId=" + URLEncoder.encode(gantryId, "UTF-8");
        }
        if(StringUtils.isNotEmpty(gantryId) && StringUtils.isNotEmpty(picId)){
            param += "&";
        }
        if(StringUtils.isNotEmpty(picId)){
            param += "picId=" + URLEncoder.encode(picId, "UTF-8");
        }
        BufferedReader bReader = null;
        String line, resultStr = null;
        try {
            out.writeBytes(param);

            //======传递参数end======
            bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            resultStr = "";
            while (null != (line = bReader.readLine())) {
                resultStr += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert bReader != null;
            bReader.close();
            out.flush();
            out.close();
            line = null;

        }


        //解析嵌套JSON
        JSONObject jsonobject = JSONObject.parseObject(resultStr);
        String code = jsonobject.get("code").toString();
        if (!"200".equals(code)) {
            result.setSuccess(false);
            result.setMessage("获取门架图片失败");
            return result;
        }
        result.setSuccess(true);
        result.setData(jsonobject.get("data"));
        result.setMessage("获取门架图片成功");

        resultStr = null;

        return result;
    }
    
    //获取集装箱预约图片
    public static ResultMsg getAptImag(String path) throws Exception {
        ResultMsg result = new ResultMsg();
        URL restURL = new URL(path);
        /*
         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
         */
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();// 设置是否从httpUrlConnection读入，默认情况下是true;
        conn.setDoInput(true);
        // 设置是否向httpUrlConnection输出
        conn.setDoOutput(true);
        // Post 请求不能使用缓存
        conn.setUseCaches(false);
        // 设定请求的方法，默认是GET
        conn.setRequestMethod("POST");
        // 设置字符编码连接参数
        conn.setRequestProperty("Connection", "Keep-Alive");
        // 设置字符编码
        conn.setRequestProperty("Charset", "UTF-8");
        // 设置请求内容类型
        conn.setRequestProperty("Content-Type", "application/json");
        // 设置DataOutputStream
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        //======传递参数======
		/*
		 * String param = ""; if(StringUtils.isNotEmpty(path)){ param += "objectURL=" +
		 * URLEncoder.encode(path, "UTF-8"); }
		 */
        
        BufferedReader bReader = null;
        String line, resultStr = null;
        try {
            //out.writeBytes(param);

            //======传递参数end======
            bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            resultStr = "";
            while (null != (line = bReader.readLine())) {
                resultStr += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert bReader != null;
            bReader.close();
            out.flush();
            out.close();
            line = null;

        }


        //解析嵌套JSON
        JSONObject jsonobject = JSONObject.parseObject(resultStr);
        
        result.setSuccess(true);
        result.setData(jsonobject.get("result"));
        result.setMessage("获取预约图片成功");

        resultStr = null;

        return result;
    }
}