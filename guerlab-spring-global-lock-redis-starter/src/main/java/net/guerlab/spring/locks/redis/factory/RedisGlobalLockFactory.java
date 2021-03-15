package net.guerlab.spring.locks.redis.factory;

import net.guerlab.spring.locks.AbstractGlobalLock;
import net.guerlab.spring.locks.factory.GlobalLockFactory;
import net.guerlab.spring.locks.redis.RedisGlobalLock;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的全局锁工厂实现
 *
 * @author guer
 */
public class RedisGlobalLockFactory implements GlobalLockFactory {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisGlobalLockFactory(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public AbstractGlobalLock build(String key, long lockTime, TimeUnit lockTimeUnit) {
        return new RedisGlobalLock(key, lockTime, lockTimeUnit, redisTemplate);
    }
}
