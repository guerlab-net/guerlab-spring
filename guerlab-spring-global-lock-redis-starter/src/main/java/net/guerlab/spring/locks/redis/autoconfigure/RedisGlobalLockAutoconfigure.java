package net.guerlab.spring.locks.redis.autoconfigure;

import net.guerlab.spring.locks.redis.factory.RedisGlobalLockFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis全局锁自动配置
 *
 * @author guer
 */
@Configuration
public class RedisGlobalLockAutoconfigure {

    /**
     * 构造基于Redis的全局锁工厂实现
     *
     * @param redisTemplate
     *         redisTemplate
     * @return 基于Redis的全局锁工厂实现
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean({ RedisTemplate.class })
    public RedisGlobalLockFactory redisGlobalLockFactory(RedisTemplate<String, Object> redisTemplate) {
        return new RedisGlobalLockFactory(redisTemplate);
    }
}
