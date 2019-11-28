package net.guerlab.spring.task.autoconfig;

import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import net.guerlab.spring.task.annotation.TaskLock;
import net.guerlab.spring.task.entity.TaskNodeInfo;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 抽象任务
 *
 * @author guer
 *
 */
@ConditionalOnClass({
        RedisTemplate.class, EnableScheduling.class
})
public class TaskLockAutoConfig {

    /**
     * Task Lock's Aop
     *
     * @author guer
     *
     */
    @Aspect
    @Component
    public static class TaskLockAop {

        private RedisTemplate<String, Object> redisTemplate;

        @Autowired
        public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
            this.redisTemplate = redisTemplate;
        }

        /**
         * task run before aop
         *
         * @param point
         *            task
         * @return task method return object
         * @throws Throwable
         *             Throws Throwable when running an error.
         */
        @Around("@annotation(net.guerlab.spring.task.annotation.TaskLock)")
        public Object deBefore(ProceedingJoinPoint point) throws Throwable {
            Signature signature = point.getSignature();

            if (!(signature instanceof MethodSignature)) {
                return proceed(point);
            }

            MethodSignature methodSignature = (MethodSignature) signature;
            TaskLock lock = methodSignature.getMethod().getAnnotation(TaskLock.class);

            String key = lock.key();

            if (StringUtils.isBlank(key)) {
                return proceed(point);
            }

            Object result = null;

            if (lock(lock, methodSignature.getDeclaringTypeName())) {
                result = proceed(point);
                unlock(key);
            }

            return result;
        }

        private Object proceed(ProceedingJoinPoint point) throws Throwable {
            Object[] args = point.getArgs();
            return args != null ? point.proceed(args) : point.proceed();
        }

        private boolean lock(TaskLock lock, String className) {
            TaskNodeInfo info = new TaskNodeInfo();

            info.setClassName(className);
            info.setApplicationName(getApplicationName());

            String key = lock.key();
            Boolean hasKey = redisTemplate.hasKey(key);

            if (hasKey != null && hasKey) {
                return false;
            }

            long timeout = lock.timeOut();

            if (timeout < 0) {
                redisTemplate.opsForValue().set(key, info);
            } else {
                redisTemplate.opsForValue().set(key, info, timeout, lock.timeUnit());
            }
            return true;
        }

        private void unlock(String key) {
            redisTemplate.delete(key);
        }

        private String getApplicationName() {
            Environment env = SpringApplicationContextUtil.getContext().getEnvironment();

            return env == null ? "" : env.getProperty("spring.application.name");
        }
    }
}
