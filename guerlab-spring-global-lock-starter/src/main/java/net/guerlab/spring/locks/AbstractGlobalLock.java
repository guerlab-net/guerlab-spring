package net.guerlab.spring.locks;

import java.util.concurrent.TimeUnit;

/**
 * 抽象全局锁
 *
 * @author guer
 */
public abstract class AbstractGlobalLock {

    protected final String key;

    protected final long lockTime;

    protected final TimeUnit lockTimeUnit;

    public AbstractGlobalLock(String key, long lockTime, TimeUnit lockTimeUnit) {
        this.key = key;
        this.lockTime = lockTime;
        this.lockTimeUnit = lockTimeUnit;
    }

    public abstract void lock();

    public abstract boolean tryLock();

    public abstract void unlock();
}
