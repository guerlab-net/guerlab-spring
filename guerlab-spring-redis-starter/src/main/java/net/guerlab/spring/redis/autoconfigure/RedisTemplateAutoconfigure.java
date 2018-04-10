package net.guerlab.spring.redis.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.guerlab.spring.cloud.commons.autoconfigure.ObjectMapperAutoconfigure;

/**
 * RedisTemplate自动配置
 * 
 * @author guer
 *
 */
@Configuration
public class RedisTemplateAutoconfigure {

    @Bean
    public RedisTemplate<String, ?> redisTemplate(
            RedisConnectionFactory factory) {

        ObjectMapper mapper = new ObjectMapperAutoconfigure().objectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        RedisTemplate<String, ?> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);

        template.setKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));

        return template;
    }
}
