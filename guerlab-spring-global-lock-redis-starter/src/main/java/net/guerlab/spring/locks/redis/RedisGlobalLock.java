package net.guerlab.spring.locks.redis;

import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import net.guerlab.spring.locks.AbstractGlobalLock;
import net.guerlab.spring.locks.redis.entity.NodeInfo;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * redis锁实现
 *
 * @author guer
 */
public class RedisGlobalLock extends AbstractGlobalLock {

    private final RedisTemplate<String, Object> redisTemplate;

    private final java.util.concurrent.atomic.AtomicLong count = new AtomicLong();

    public RedisGlobalLock(String key, long lockTime, TimeUnit lockTimeUnit,
            RedisTemplate<String, Object> redisTemplate) {
        super(key, lockTime, lockTimeUnit);
        this.redisTemplate = redisTemplate;
    }

    private static NodeInfo buildNodeInfo() {
        return new NodeInfo(SpringApplicationContextUtil.getApplicationName());
    }

    @Override
    public void lock() {
        count.incrementAndGet();
        //noinspection StatementWithEmptyBody
        while (hasKey()) {
            // 此处自旋，检查key是否被释放
        }
        setValue();
    }

    @Override
    public boolean tryLock() {
        count.incrementAndGet();
        if (hasKey()) {
            count.decrementAndGet();
            return false;
        }
        setValue();
        return true;
    }

    @Override
    public void unlock() {
        if (count.get() > 0L) {
            count.decrementAndGet();
        } else {
            redisTemplate.delete(key);
        }
    }

    private boolean hasKey() {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }

    private void setValue() {
        if (lockTime < 0) {
            redisTemplate.opsForValue().set(key, buildNodeInfo());
        } else {
            redisTemplate.opsForValue().set(key, buildNodeInfo(), lockTime, lockTimeUnit);
        }
    }
}
