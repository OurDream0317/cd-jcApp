package com.tuozhi.zhlw.admin.jcApp;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppResultMsg<T> implements Serializable {
    private String message;
    private boolean success;
    private T data;
    private Integer type;
}
