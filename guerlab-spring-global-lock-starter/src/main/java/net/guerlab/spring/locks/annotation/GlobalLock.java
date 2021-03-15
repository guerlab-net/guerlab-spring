package net.guerlab.spring.locks.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 全局锁
 *
 * @author guer
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GlobalLock {

    /**
     * 获取锁的名称
     *
     * @return 锁名称
     */
    String key();

    /**
     * 获取锁超时时间
     *
     * @return 锁超时时间
     */
    long lockTime() default -1L;

    /**
     * 获取锁超时时间单位
     *
     * @return 锁超时时间单位
     */
    TimeUnit lockTimeUnit() default TimeUnit.MILLISECONDS;
}
