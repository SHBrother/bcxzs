package com.edingyc.bcxzs.Utils;

import java.util.HashMap;
import java.util.Map;

public class EbusRequestSession
{
    // openid
    public final static String HEADER_USERID = "openid";

    
    private static ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<Map<String, String>>();
    
    public static void setExHeaders(Map<String, String> value)
    {
        threadLocal.set(value);
    }
    
    public static void addExHeader(String key, String value)
    {
        Map<String, String> map = threadLocal.get();
        if (map == null)
        {
            map = new HashMap<String, String>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }
    
    public static String getExHeader(String key)
    {
        Map<String, String> map = threadLocal.get();
        if (map == null)
        {
            map = new HashMap<String, String>();
            threadLocal.set(map);
        }
        return threadLocal.get().get(key);
    }
    
    public static Map<String, String> getExHeaders()
    {
        return threadLocal.get();
    }
    
    public static void removeSession()
    {
        threadLocal.remove();
    }
    
}
