package com.edingyc.bcxzs.LogManage;

import java.lang.annotation.*;

/**
 * service日志注解类
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseLog {

    String description() default "";

}
