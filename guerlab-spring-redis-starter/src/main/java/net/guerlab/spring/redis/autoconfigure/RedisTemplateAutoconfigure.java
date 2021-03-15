package net.guerlab.spring.redis.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisTemplate自动配置
 *
 * @author guer
 *
 */
@Configuration
public class RedisTemplateAutoconfigure {

    /**
     * create RedisTemplate
     *
     * @param factory
     *            RedisConnectionFactory
     * @param objectMapper
     *            objectMapper
     * @return RedisTemplate
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean({ RedisConnectionFactory.class, ObjectMapper.class })
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        ObjectMapper mapper = objectMapper.copy();

        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

        RedisTemplate<String, ?> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);

        template.setKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));

        return template;
    }
}
