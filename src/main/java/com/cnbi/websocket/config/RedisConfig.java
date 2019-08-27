package com.cnbi.websocket.config;

import com.cnbi.websocket.utils.CacheManager;
import com.cnbi.websocket.utils.TemplateCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * @ClassName RedisConfig
 * @Description
 * @Author Wangjunkai
 * @Date 2019/8/26 13:57
 **/
@Configuration
public class RedisConfig {

    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<String, Object> getRedisClient(RedisConnectionFactory factory){
        RedisTemplate<String, Object> redisClient = new RedisTemplate<>();
        redisClient.setKeySerializer(new JdkSerializationRedisSerializer());
        redisClient.setDefaultSerializer(new JdkSerializationRedisSerializer());
        redisClient.setConnectionFactory(factory);
        return redisClient;
    }

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager getCacheManager(RedisTemplate<String, Object> template){
        return new TemplateCacheManager(template);
    }
}