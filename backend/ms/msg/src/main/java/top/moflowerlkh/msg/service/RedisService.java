package top.moflowerlkh.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public Set<String> keys(String keyRegex) {
        return stringRedisTemplate.keys(keyRegex);
    }

    public void increment(String key, long delta) {
        stringRedisTemplate.opsForValue().increment(key, delta);
    }

    public long decrement(String key, long delta) {
        return stringRedisTemplate.opsForValue().decrement(key, delta);
    }

    public void set(String k, String v) {
        stringRedisTemplate.opsForValue().set(k, v);
    }

    public String get(String k) {
        return stringRedisTemplate.opsForValue().get(k);
    }

    public void del(String k) {
        stringRedisTemplate.delete(k);
    }

    public void SAdd(String k, String... v) {
        for (String s : v) {
            stringRedisTemplate.opsForSet().add(k, s);
        }
    }

    public Boolean exists(String k) {
        return stringRedisTemplate.hasKey(k);
    }


}
