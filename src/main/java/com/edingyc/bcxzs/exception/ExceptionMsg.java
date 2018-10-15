package com.edingyc.bcxzs.exception;

public enum ExceptionMsg {

    SUCCESS(0, "success"),
    SystemError(1,"systemError"),
    LoginError(2, "login error"),
    DTO2EntityError(3,"fail to convert DTO to entity"),
    DuplicateVinError(4,"duplicate vin"),
    MD5ParseError(5,"md5 parse error"),
    DateFormatError(6,"date format error");

    ExceptionMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
