package com.tuozhi.zhlw.admin.jc.util;

/**
 * 数据重复异常
 */
public class DataRepetitiveException extends Exception {
    private static final long serialVersionUID = -5521163700838944563L;

    public DataRepetitiveException() {

    }

    public DataRepetitiveException(String message) {
        super(message);
    }
}
