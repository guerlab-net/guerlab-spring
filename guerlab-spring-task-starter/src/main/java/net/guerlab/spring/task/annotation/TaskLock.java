package net.guerlab.spring.task.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 任务锁
 *
 * @author guer
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TaskLock {

    /**
     * 获取锁的名称
     *
     * @return 锁名称
     */
    String key();

    /**
     * 获取超时时间
     *
     * @return 超时时间
     */
    long timeOut() default -1L;

    /**
     * 获取超时时间单位
     *
     * @return 超时时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
