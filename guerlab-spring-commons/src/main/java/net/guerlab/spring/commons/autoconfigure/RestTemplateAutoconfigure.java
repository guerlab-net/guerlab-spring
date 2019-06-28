package net.guerlab.spring.commons.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * RestTemplate自动配置
 *
 * @author guer
 *
 */
@Configuration
@AutoConfigureAfter(ObjectMapperAutoconfigure.class)
public class RestTemplateAutoconfigure {

    /**
     * 初始化LoadBalancedRestTemplate
     *
     * @param objectMapper objectMapper
     *
     * @return LoadBalancedRestTemplate
     */
    @Bean
    @LoadBalanced
    @ConditionalOnClass({
            LoadBalanced.class, RestTemplate.class
    })
    @ConditionalOnMissingBean(value = RestTemplate.class, annotation = LoadBalanced.class)
    public RestTemplate loadBalancedRestTemplate(ObjectMapper objectMapper) {
        return createRestTemplate(objectMapper);
    }

    /**
     * 初始化RestTemplate
     *
     * @param objectMapper objectMapper
     *
     * @return RestTemplate
     */
    @Bean
    @Primary
    @ConditionalOnClass(RestTemplate.class)
    @ConditionalOnMissingBean(value = RestTemplate.class)
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        return createRestTemplate(objectMapper);
    }

    private RestTemplate createRestTemplate(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter(objectMapper),
                new StringHttpMessageConverter(), new FormHttpMessageConverter()));

        return restTemplate;
    }
}
