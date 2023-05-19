package com.xiaoao.mall.exception;

public enum XiaoaoMallExceptionEnum {
    NEED_USER_NAME(10001,"用户名不能为空"),
    NEED_PASSWORD(10002,"密码不能为空"),
    PASSWORD_TOO_SHORT(10003,"密码至少需要8位"),
    NAME_EXISTED(10004,"用户名已存在"),
    INSERT_FAILED(10005,"插入失败，请重试！"),
    WRONG_PASSWORD(10006,"密码错误！"),
    SYSTEM_ERROR(20001,"系统异常"),

    TOKEN_EXPIRED(10029, "token过期"),
    TOKEN_WRONG(10030, "token解析失败"),
    NEED_CARD_ID(30001,"需要添加银行卡开通钱包" ),
    BANK_INSERT_FAILED(30002,"银行卡加入失败"),
    SUB_MONEY_FALIED(30003,"扣款失败" ),

    ADD_MONEY_FALIED(30004,"退款失败，请联系管理员处理！" ),

    NEED_OPEN_WALLET(30005,"请先开通钱包" );



    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    XiaoaoMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
