package com.tuozhi.zhlw.common.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author  linqi
 * rest 返回消息bean
 */

@Data
public class ResultMsg<T> implements Serializable {
    private String message;
    private boolean success;
    //private Map<String,Object> data;
    private T data;
    private T obj;
    private int totalProperty;
}
