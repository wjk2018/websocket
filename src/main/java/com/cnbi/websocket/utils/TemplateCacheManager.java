package com.cnbi.websocket.utils;

import org.springframework.data.redis.core.*;

import java.util.Map;

/**
 * @ClassName TemplateCacheManager
 * @Description
 * @Author Wangjunkai
 * @Date 2019/8/26 15:33
 **/

public class TemplateCacheManager implements CacheManager {

    public TemplateCacheManager(RedisTemplate<String, Object> redisTemplate){
        super();
        this.redisTemplate = redisTemplate;
        init(redisTemplate);
    }

    protected RedisTemplate<String, Object> redisTemplate;
    //String
    protected ValueOperations<String, Object> valueOperations;
    //map
    protected HashOperations<String, String, Object> hashOperations;
    //list
    protected ListOperations<String, Object> listOperations;
    //无序set
    protected SetOperations<String, Object> setOperations;
    //有序set
    protected ZSetOperations<String, Object> zSetOperations;

    private void init(RedisTemplate<String, Object> redisTemplate){
        valueOperations = redisTemplate.opsForValue();
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
        setOperations = redisTemplate.opsForSet();
        zSetOperations = redisTemplate.opsForZSet();
    }

    @Override
    public Object get(String key) {
        return valueOperations.get(key);
    }

    @Override
    public void set(String key, Object Value) {
        valueOperations.set(key, Value);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Object hGet(String key, String hKey){
        return hashOperations.get(key, hKey);
    }

    @Override
    public Map hGet(String key){
        return hashOperations.entries(key);
    }

    @Override
    public void hSet(String key, String hKey, Object value){
        hashOperations.put(key, hKey, value);
    }

    @Override
    public void hDelete(String key, String hKey){
        hashOperations.delete(key, hKey);
    }
}