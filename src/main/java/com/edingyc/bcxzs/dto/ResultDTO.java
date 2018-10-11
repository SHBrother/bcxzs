package com.edingyc.bcxzs.dto;

import lombok.Data;

/**
 * @program: bcxzs
 * @description: result dto
 * @author: YSM
 * @create: 2018-09-25 21:35
 **/
@Data
public class ResultDTO<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;

}
