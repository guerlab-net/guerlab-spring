package net.guerlab.spring.redis.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

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
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        ObjectMapper mapper = objectMapper.copy();
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new GenericJackson2JsonRedisSerializer(mapper));
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));

        return template;
    }
}
