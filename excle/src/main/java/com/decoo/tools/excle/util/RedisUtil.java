package com.decoo.tools.excle.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ghd
 * @date 2019/12/11 15:44
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 获取对应的value
     *
     * @param key key
     */
    Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置值
     * @param key key
     * @param val value
     * @return boolean
     */
    boolean setValue(String key, String val) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, val);
            result = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory("localhost", 6379);

}
