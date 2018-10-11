package com.edingyc.bcxzs.Utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @program: bcxzs
 * @description: cache
 * @author: YSM
 * @create: 2018-09-30 17:13
 **/
public class CacheUtils {

    private static Cache<String,String> cache = CacheBuilder.newBuilder().expireAfterWrite(24, TimeUnit.HOURS).build();

    public static void setCache(String key,String value){
        cache.put(key,value);
    }

    public static String getCache(String key){
        return cache.getIfPresent(key);

    }


}
