package com.cnbi.websocket.utils;

import java.util.Map;

/**
 * @ClassName CacheManager
 * @Description
 * @Author Wangjunkai
 * @Date 2019/8/26 15:30
 **/

public interface CacheManager {

    public final String AUTH_PREFIX = "AUTH";

    public final String defaultPrefix = "WS_SERVER";

    Object get(String key);

    void set(String key, Object Value);

    void delete(String key);

    Object hGet(String key, String hKey);

    Map hGet(String key);

    void hSet(String key, String hKey, Object value);

    void hDelete(String key, String hKey);
}