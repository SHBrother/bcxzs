package com.edingyc.bcxzs.Utils;

import com.edingyc.bcxzs.dto.ResultDTO;
import com.edingyc.bcxzs.exception.ExceptionMsg;

/**
 * @program: bcxzs
 * @description: result util
 * @author: YSM
 * @create: 2018-09-25 21:35
 **/
public class ResultUtil {

    public static ResultDTO success(Object object) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setData(object);
        resultDTO.setCode(ExceptionMsg.SUCCESS.getCode());
        resultDTO.setMsg(ExceptionMsg.SUCCESS.getMsg());
        return resultDTO;
    }

    public static ResultDTO success() {
        return success(null);
    }

    public static ResultDTO error(Integer code, String msg) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMsg(msg);
        return resultDTO;
    }


}
