package com.tuozhi.zhlw.common.utils;

import java.util.UUID;

/**
 * @author linqi
 * @create 2019/09/05 0:33
 **/
public class IdProvider {
    public static String createUUIDId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
