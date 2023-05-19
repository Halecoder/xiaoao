package com.xiaoao.mall.exception;


/**
 * 描述：自定义、统一异常；
 */
public class XiaoaoMallException extends Exception {
    private final Integer code;
    private final String message;


    public XiaoaoMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public XiaoaoMallException(XiaoaoMallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
  