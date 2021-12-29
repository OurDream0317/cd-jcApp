package com.tuozhi.zhlw.common.base.mymapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * 获取消息码对应的内容
 */
public class CdMessageUtil {

    private static final String File = "CdMessage.properties";
    //	private static final String webPaht = System.getProperty("cd-basedata-web-admin") + "/WEB-INF/classes/";
    private static final String webPaht = System.getProperty("user.dir") + "/";
    private static Properties props = null;

    public static boolean fileExited() {
        File file = new File(webPaht + File);
        return file.exists();
    }

    public static String readDate(String key) {
        String value = null;
        if (props == null) {
            props = new Properties();

            InputStream in = null;
            try {
                in = CdMessageUtil.class.getClassLoader().getResourceAsStream("CdMessage.properties");
                props.load(in);
            } catch (IOException ex) {

            } finally {
                try {
                    assert in != null;
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        value = props.getProperty(key);
        return value;
    }
}
