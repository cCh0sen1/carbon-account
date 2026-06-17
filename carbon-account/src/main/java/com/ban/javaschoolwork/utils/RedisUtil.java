package com.ban.carbonaccount.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class CacheHelper {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // ============ 字符串操作 ============

    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void put(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public void put(String key, Object value, long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unchecked")
    public <T> T fetch(String key) {
        if (key == null) return null;
        return (T) redisTemplate.opsForValue().get(key);
    }

    // ============ 存在性判断 ============

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    // ============ 删除操作 ============

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public void remove(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    // ============ 过期时间 ============

    public boolean expire(String key, Duration timeout) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout));
    }

    // ============ 计数器（适合碳积分临时计数等场景） ============

    public long increment(String key, long delta) {
        Long result = redisTemplate.opsForValue().increment(key, delta);
        return result != null ? result : 0L;
    }

    public long decrement(String key, long delta) {
        Long result = redisTemplate.opsForValue().decrement(key, delta);
        return result != null ? result : 0L;
    }

    // ============ 排行榜操作（碳积分排行） ============

    public void rankAdd(String key, String member, double score) {
        redisTemplate.opsForZSet().add(key, member, score);
    }

    public Set<ZSetOperations.TypedTuple<Object>> rankTop(String key, int count) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, count - 1);
    }

    public Double rankScore(String key, String member) {
        return redisTemplate.opsForZSet().score(key, member);
    }

    // ============ 模糊匹配扫描 ============

    public Set<String> scan(String pattern) {
        Set<String> keys = new HashSet<>();
        try (Cursor<String> cursor = redisTemplate.scan(
                ScanOptions.scanOptions().match(pattern).count(100).build())) {
            cursor.forEachRemaining(keys::add);
        }
        return keys;
    }
}
