package com.tuozhi.zhlw.common.utils;

import com.tuozhi.zhlw.common.bean.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName CommonUtils
 * @Descriotion TODO
 * @Author fujiankang
 * @Date 2019/10/23 9:39
 * @Version 1.0
 */
@Slf4j
@Configuration
public class CommonUtils {
    /**
     * 通用的系统错误信息返回
     *
     * @param e
     * @return
     */
    public static ResultMsg systemErrorDispose(Exception e, ResultMsg result) {
        log.error(e.getMessage());
        result = new ResultMsg();
        result.setSuccess(false);
        result.setMessage("系统错误");
        return result;
    }

    /**
     * 运行时异常统一处理
     *
     * @param e
     * @param result
     * @return
     */
    public static ResultMsg runErrorDispose(RuntimeException e, ResultMsg result) {
        result = new ResultMsg();
        result.setSuccess(false);
        result.setMessage(e.getMessage());
        log.error(e.getMessage());
        return result;
    }

    /**
     * 上传文件
     *
     * @param file
     * @param filePath
     * @throws Exception
     */
    public static String upFile(MultipartFile file, String fileOutPath, String filePath) throws Exception {
        try {
            File file1 = new File(fileOutPath + "\\" + filePath);
            //判断文件存储路径是否存在
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdirs();
            }
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream in = file.getInputStream();
            //创建一个文件输出流
            FileOutputStream out = new FileOutputStream(file1.getPath() + "\\" + file.getOriginalFilename());
            //创建一个缓冲区
            byte buffer[] = new byte[1024];
            //判断输入流中的数据是否已经读完的标识
            int len = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while ((len = in.read(buffer)) > 0) {
                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                out.write(buffer, 0, len);
            }
            //关闭输入流
            in.close();
            //关闭输出流
            out.close();
            return file1.getPath();
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception("附件上传失败");
        }
    }

    /**
     * 匹配文件后缀类型
     *
     * @param file
     * @return
     */
    public static int getFileType(MultipartFile file) {
        int fileType;
        String name = file.getOriginalFilename();
        if (name.endsWith("mp4") || name.endsWith("rmvb")) {
            fileType = 1;
        } else if (name.endsWith(".mp3")) {
            fileType = 2;
        } else if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith("png") || name.endsWith("gif")) {
            fileType = 3;
        } else if (name.endsWith(".html") || name.endsWith(".zip") || name.endsWith(".txt") || name.endsWith(".doc") || name.endsWith(".xls")||name.endsWith(".xlsx") || name.endsWith(".pdf")) {
            fileType = 4;
        } else {
            fileType = 5;
        }
        return fileType;
    }

    public static int getFileType(String fileName) {
        int fileType;
        String name = fileName;
        if (name.endsWith("mp4") || name.endsWith("rmvb")) {
            fileType = 1;
        } else if (name.endsWith(".mp3")) {
            fileType = 2;
        } else if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith("png") || name.endsWith("gif")) {
            fileType = 3;
        } else if (name.endsWith(".html") || name.endsWith(".zip") || name.endsWith(".txt") || name.endsWith(".doc") || name.endsWith(".xls")||name.endsWith(".xlsx") || name.endsWith(".pdf")) {
            fileType = 4;
        } else {
            fileType = 5;
        }
        return fileType;
    }

    /**
     * 下载文件
     *
     * @param filePath
     * @param response
     * @return
     */
    public static ResultMsg downloadFile(String resourcaLocation, String filePath, HttpServletResponse response) {
        ResultMsg result = new ResultMsg();
        File file = null;
        try {
            file = new File(ResourceUtils.getFile(resourcaLocation), filePath);
            if (!file.exists()) {
                result.setSuccess(false);
                result.setMessage("该文件不存在");
                return result;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("上传失败");
            return result;
        }
        // 读到流中
        InputStream inStream = null;// 文件的存放路径
        try {
            inStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("上传失败");
            return result;
        }
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        // 设置输出的格式
        response.reset();
        response.setContentType("application/octet-stream");
        try {
            response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("上传失败");
            return result;
        }
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("上传失败");
            return result;
        }
        return null;
    }

    /**
     * 文件下载新版
     *
     * @param filePath
     * @param response
     * @return
     */
    public static void downloadFile1(String filePath, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(filePath);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}