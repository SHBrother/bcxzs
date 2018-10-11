package com.edingyc.bcxzs.LogManage;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * @program: bcxzs
 * @description: log aspect
 * @author: YSM
 * @create: 2018-09-04 10:14
 **/
@Slf4j
@Aspect
@Service
public class LogAspect {

    @Pointcut("@annotation(com.edingyc.bcxzs.LogManage.BaseLog)")
    public void baseAspect(){

    }


    /**
     * 前置通知 记录用户进入方法时的日志
     * @param joinPoint
     */
    @Before("baseAspect()")
    public void doBefore4service(JoinPoint joinPoint) {

        try {
            //切点目标
            Object target = joinPoint.getTarget();
            String className = target.getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            //获取用户请求方法的参数并序列化为JSON格式字符串
            String params = "";
            if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {
                for ( int i = 0; i < joinPoint.getArgs().length; i++) {
                    params += JSONObject.toJSON(joinPoint.getArgs()[i]).toString() + ";";
                }
            }
            log.info(">>> "+className+"."+methodName+" with reqParam = {}",params);

        } catch (Exception e) {
            log.error("logErrMsg = {}", e.getMessage());
        }
    }

   /**
     * 后置通知 记录方法返回时的日志
     * @param joinPoint
     * @param returnValue
     */
    @AfterReturning(pointcut = "baseAspect()",returning = "returnValue")
    public  void after4service(JoinPoint joinPoint, Object returnValue) {
        try {
            //切点目标
            Object target = joinPoint.getTarget();
            String className = target.getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String respParam = JSONObject.toJSON(returnValue).toString();

            log.info("<<< "+className+"."+methodName+" with respParam = {}" ,respParam);

        } catch (Exception e) {
            log.error("logErrMsg = {}", e.getMessage());
        }

    }

    /**
     * 方法抛出异常时的日志
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "baseAspect()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {

        //获取用户请求方法的参数并组织成字符串
        String params = "";
        if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {
            for ( int i = 0; i < joinPoint.getArgs().length; i++) {
                params += JSONObject.toJSON(joinPoint.getArgs()[i]).toString() + ";";
            }
        }
        try {
            String errStack = ExceptionUtils.getStackTrace(e);
            log.error("*** "+"with reqParam = {}" +"errStack = {}",params,errStack);
        } catch (Exception ex) {
            log.error("logErrMsg = {}", ex.getMessage());
        }
    }

    /**
     * 获取方法注解描述
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getServiceMethodDesc(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(BaseLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

}
