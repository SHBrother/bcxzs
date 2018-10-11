package com.edingyc.bcxzs.Utils.convert;

import com.edingyc.bcxzs.exception.ExceptionMsg;
import com.edingyc.bcxzs.exception.WrapException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @program: bcxzs
 * @description: car transport dto to car receive dto
 * @author: YSM
 * @create: 2018-09-26 14:14
 **/
@Slf4j
public class ConvertUtil {

    public static <T> T convert(Object origin,Class<T> resultClazz) throws WrapException{
        if (origin == null){
            return null;
        }
        Class originClazz = origin.getClass();
        T result;
        try {
            result = resultClazz.newInstance();
        } catch (Exception e) {
            log.error("convertError={}", ExceptionUtils.getStackTrace(e));
            throw new WrapException(ExceptionMsg.DTO2EntityError.getCode(),ExceptionMsg.DTO2EntityError.getMsg());
        }
        Field[] originFields = originClazz.getDeclaredFields();
        Field[] resultFields = resultClazz.getDeclaredFields();
        List<String> resultFieldNameList = new ArrayList<>();
        for (Field field:resultFields) {
            resultFieldNameList.add(field.getName());
        }
        for (int i=0;i<originFields.length;i++){
            try {
                originFields[i].setAccessible(true);
                Object value = originFields[i].get(origin);
                String name = originFields[i].getName();
                if (value == null || originFields[i].isAnnotationPresent(IgnoreField.class) ){
                    continue;
                }

                if (resultFieldNameList.contains(name)){
                    Field resultField = resultClazz.getDeclaredField(name);
                    resultField.setAccessible(true);
                    resultField.set(result,value);
                }

            } catch (Exception e) {
                log.error("convertError={}", ExceptionUtils.getStackTrace(e));
                throw new WrapException(ExceptionMsg.DTO2EntityError.getCode(),ExceptionMsg.DTO2EntityError.getMsg());
            }

        }
        return result;
    }

    public static <T,V> List<T> convert(List<V> origin,Class<T> resultClazz)  throws WrapException {
        List<T> tList = new ArrayList<>();
        for (V v : origin) {
            T t = convert(v,resultClazz);
            if (t==null) continue;
            tList.add(t);
        }
        return tList;
    }
}
