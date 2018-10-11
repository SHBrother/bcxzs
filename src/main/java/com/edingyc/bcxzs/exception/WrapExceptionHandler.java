package com.edingyc.bcxzs.exception;

import com.edingyc.bcxzs.Utils.ResultUtil;
import com.edingyc.bcxzs.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: bcxzs
 * @description: wrap exception handler
 * @author: YSM
 * @create: 2018-09-25 22:04
 **/
@ControllerAdvice
@Slf4j
public class WrapExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResultDTO handle(Exception e){
        if (e instanceof WrapException){
            WrapException wrapException = (WrapException)e;
            return ResultUtil.error(wrapException.getCode(),wrapException.getMessage());
        }else{
            log.error("systemError ={}", ExceptionUtils.getStackTrace(e));
            return ResultUtil.error(ExceptionMsg.SystemError.getCode(),ExceptionMsg.SystemError.getMsg());

        }
    }


}
