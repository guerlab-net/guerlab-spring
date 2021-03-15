package net.guerlab.spring.locks.factory;

import net.guerlab.spring.locks.AbstractGlobalLock;

import java.util.concurrent.TimeUnit;

/**
 * 全局锁工厂
 *
 * @author guer
 */
public interface GlobalLockFactory {

    /**
     * 根据锁名称构造全局锁
     *
     * @param key
     *         锁名称
     * @param lockTime
     *         锁超时时间
     * @param lockTimeUnit
     *         锁超时时间单位
     * @return 全局锁
     */
    AbstractGlobalLock build(String key, long lockTime, TimeUnit lockTimeUnit);

    /**
     * 根据锁名称构造全局锁
     *
     * @param key
     *         锁名称
     * @param lockTime
     *         锁超时时间
     * @return 全局锁
     */
    default AbstractGlobalLock build(String key, long lockTime) {
        return build(key, -1, TimeUnit.MILLISECONDS);
    }
}
