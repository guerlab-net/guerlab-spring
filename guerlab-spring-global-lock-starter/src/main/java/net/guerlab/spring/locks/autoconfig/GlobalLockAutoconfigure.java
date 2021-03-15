package net.guerlab.spring.locks.autoconfig;

import net.guerlab.spring.locks.AbstractGlobalLock;
import net.guerlab.spring.locks.annotation.GlobalLock;
import net.guerlab.spring.locks.factory.GlobalLockFactory;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 抽象任务
 *
 * @author guer
 */
@Configuration
@ConditionalOnBean({ GlobalLockFactory.class })
public class GlobalLockAutoconfigure {

    /**
     * Task Lock's Aop
     *
     * @author guer
     */
    @Aspect
    @Component
    public static class GlobalLockAop {

        private GlobalLockFactory factory;

        @Autowired
        public void setRedisTemplate(GlobalLockFactory factory) {
            this.factory = factory;
        }

        /**
         * task run before aop
         *
         * @param point
         *         task
         * @return task method return object
         * @throws Throwable
         *         Throws Throwable when running an error.
         */
        @Around("@annotation(net.guerlab.spring.locks.annotation.GlobalLock)")
        public Object deBefore(ProceedingJoinPoint point) throws Throwable {
            Signature signature = point.getSignature();

            if (!(signature instanceof MethodSignature)) {
                return proceed(point);
            }

            MethodSignature methodSignature = (MethodSignature) signature;
            GlobalLock lock = methodSignature.getMethod().getAnnotation(GlobalLock.class);

            String key = StringUtils.trimToNull(lock.key());

            if (key == null) {
                return proceed(point);
            }

            AbstractGlobalLock globalLock = factory.build(key, lock.lockTime(), lock.lockTimeUnit());

            try {
                globalLock.lock();
                return proceed(point);
            } finally {
                globalLock.unlock();
            }
        }

        private Object proceed(ProceedingJoinPoint point) throws Throwable {
            Object[] args = point.getArgs();
            return args != null ? point.proceed(args) : point.proceed();
        }
    }
}
