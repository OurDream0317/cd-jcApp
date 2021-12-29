package com.tuozhi.zhlw.common.exception;

import com.tuozhi.zhlw.common.enums.ResultCodeEnum;

/**
 * @author linqi
 * @create 2019/09/04 23:51
 **/
public class CacheException extends RuntimeException {
    private static final long serialVersionUID = -2678203134198782909L;
    private  int code;

    public static final String MESSAGE = "缓存异常";

    public CacheException() {
        super(MESSAGE);
    }

    public CacheException(String message) {
        super(message);
        this.code = ResultCodeEnum.CACHE_ERROR.getCode();
    }

    public CacheException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCodeEnum.CACHE_ERROR.getCode();
    }

    public CacheException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CacheException(Throwable cause) {
        super(cause);
        this.code = ResultCodeEnum.CACHE_ERROR.getCode();
    }

}
