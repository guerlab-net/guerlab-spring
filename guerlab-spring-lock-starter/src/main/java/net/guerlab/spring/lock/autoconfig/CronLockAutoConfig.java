package net.guerlab.spring.lock.autoconfig;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around; 
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate; 
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import net.guerlab.spring.lock.annotation.CronLock;
import net.guerlab.spring.lock.entity.CronNodeInfo;

/**
 * 抽象任务
 *
 * @author guer
 *
 */
@ConditionalOnClass({
        RedisTemplate.class, EnableScheduling.class
})
public class CronLockAutoConfig {

    @Aspect
    @Component
    public static class CronAop {

        @Autowired
        private RedisTemplate<String, Object> redisTemplate;

        @Around("@annotation(net.guerlab.spring.lock.annotation.CronLock)")
        public Object deBefore(
                ProceedingJoinPoint point) throws Throwable {
            Signature signature = point.getSignature();

            if (!(signature instanceof MethodSignature)) {
                return proceed(point);
            }

            MethodSignature methodSignature = (MethodSignature) signature;
            CronLock lock = methodSignature.getMethod().getAnnotation(CronLock.class);

            String key = lock.key();

            if (StringUtils.isBlank(key)) {
                return proceed(point);
            }

            Object result = null;

            boolean locked = lock(key, lock.timeOut(), lock.timeUnit());

            if (locked) {
                result = proceed(point);
                unlock(key);
            }

            return result;
        }

        private Object proceed(
                ProceedingJoinPoint point) throws Throwable {
            Object[] args = point.getArgs();
            return args != null ? point.proceed(args) : point.proceed();
        }

        private boolean lock(
                String key,
                long timeout,
                TimeUnit unit) {
            if (!redisTemplate.opsForValue().setIfAbsent(key, new CronNodeInfo())) {
                return false;
            }

            if (timeout < 0) {
                return true;
            } else {
                return redisTemplate.expire(key, timeout, unit);
            }
        }

        private void unlock(
                String key) {
            redisTemplate.delete(key);
        }
    }
}
