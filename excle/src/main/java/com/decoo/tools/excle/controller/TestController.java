package com.decoo.tools.excle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author ghd
 * @date 2019/12/12 13:38
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final StringRedisTemplate stringRedisTemplate;
    private static final String LOCK_KEY = "lock";
    private static final String LOCK_VALUE = "call";

    @PostMapping("lock")
    public String lockPocess() {
        try {
            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(LOCK_KEY, LOCK_VALUE, 10L, TimeUnit.SECONDS);
            if (flag != null && !flag) {
                return "fail";
            }
            //处理逻辑
            log.info("当前{}线程已经拿到了redis锁", Thread.currentThread().getName());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放锁
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Boolean relaseFlag = stringRedisTemplate.execute(new DefaultRedisScript<>(script), Collections.singletonList(LOCK_KEY), LOCK_VALUE);
            if (relaseFlag != null && relaseFlag) {
                stringRedisTemplate.opsForValue().decrement(LOCK_KEY);
            }
            //如果已经过期
            String value = stringRedisTemplate.opsForValue().get(LOCK_KEY);
            if (value != null && Long.parseLong(value) < System.currentTimeMillis()) {
                String andSet = stringRedisTemplate.opsForValue().getAndSet(LOCK_KEY, LOCK_VALUE);
                // 比较锁的时间，如果不一致则可能是其他锁已经修改了值并获取
                if (andSet != null && andSet.equals(value)) {
//                    return "success";
                }
            }
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
        return "";
    }


}
