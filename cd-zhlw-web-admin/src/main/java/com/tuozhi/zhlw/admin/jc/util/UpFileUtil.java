package com.tuozhi.zhlw.admin.jc.util;

import com.tuozhi.zhlw.admin.controller.BaseController;
import com.tuozhi.zhlw.admin.jc.entity.JCOperationAttachment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class UpFileUtil extends BaseController {
    @Value("${fileOutPath}")
    private String fileOutPath;

    public JCOperationAttachment upFileMethod(MultipartFile file,String fileName, HttpServletRequest request) throws IOException {
        JCOperationAttachment jcOperationAttachment=new JCOperationAttachment();
        File file1 = null;
        long str=System.currentTimeMillis();//时间戳
            file1 = new File(fileOutPath+"/"+fileName);
            if (!file1.exists() && !file1.isDirectory()) {
                boolean mkdirs = file1.mkdirs();
            }
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream in = file.getInputStream();
            //创建一个文件输出流
            FileOutputStream out = new FileOutputStream(file1.getPath() + "\\"+str+file.getOriginalFilename());
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
        jcOperationAttachment.setAttachmentName(file.getOriginalFilename());
        jcOperationAttachment.setAttachmentSize(file.getSize());
        jcOperationAttachment.setAttachmentPath(fileOutPath+"/"+fileName+"/"+str+file.getOriginalFilename());
        String name = file.getOriginalFilename();
        if (name.endsWith("mp4") || name.endsWith("rmvb")) {
            jcOperationAttachment.setType(1);
        } else if (name.endsWith(".mp3")) {
            jcOperationAttachment.setType(2);
        } else if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith("png") || name.endsWith("gif")) {
            jcOperationAttachment.setType(3);
        } else if (name.endsWith(".html") || name.endsWith(".zip") || name.endsWith(".txt") || name.endsWith(".doc") || name.endsWith(".xls") || name.endsWith(".pdf")) {
            jcOperationAttachment.setType(4);
        } else {
            jcOperationAttachment.setType(5);
        }
        jcOperationAttachment.setUpFileUserName(getLoginUser(request).getUserName());
        jcOperationAttachment.setUpFileDept(getLoginUser(request).getDeptName());
        return jcOperationAttachment;
    }
}
