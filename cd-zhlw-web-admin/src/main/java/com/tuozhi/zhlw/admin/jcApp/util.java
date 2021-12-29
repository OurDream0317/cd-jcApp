package com.tuozhi.zhlw.admin.jcApp;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Component
public class util {
    public static String getImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = imgPath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return encode;
    }
    public static String getBase64String(MultipartFile file) throws IOException {
        InputStream inputStream;
        ByteArrayOutputStream bos;
        inputStream = file.getInputStream();
        bos = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, bos);
        byte[] bytes = bos.toByteArray();
        String base64Path = Base64.getEncoder().encodeToString(bytes);
        return base64Path;
    }
    public static Integer getFileType(MultipartFile file){
        Integer fileType;
        String name = file.getOriginalFilename().toLowerCase();
        if(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith("png") || name.endsWith("gif")){
            fileType=3;
        }else{
            fileType=1;
        }
        return fileType;
    }
}
