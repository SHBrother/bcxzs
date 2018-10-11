package com.edingyc.bcxzs.exception;

import lombok.Data;

/**
 * @program: bcxzs
 * @description: wrap exception
 * @author: YSM
 * @create: 2018-09-25 21:47
 **/
@Data
public class WrapException extends Exception{

    private Integer code;

    public WrapException(ExceptionMsg exceptionMsg) {
        super(exceptionMsg.getMsg());
        this.code = exceptionMsg.getCode();
    }

    public WrapException(Integer code, String message) {
        super(message);
        this.code = code;
    }


}
